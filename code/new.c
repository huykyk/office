/*
 * controller.c
 *
 *  Created on: Oct 14, 2014
 *      Author: root
 */

#include <stdlib.h>
#include "rt_global_var.h"
#include "opt_global_var.h"
#include "optimizeTools.h"
#include "rt_controller.h"
#include "optimize_main.h"

//static RT_FLAG* rt_flag_p;
static int state_flag = 0;
static int exist_brake_flag = 0;
static int brake_post = 0;
static int control_enable_flag = 0;
static int very_close_to_limit_flag = 0;
static int last_gear = 0;
static int count = 0;
//static int count10 = 0;
static int max_gear = 76;
static int min_gear = -100;
static float mark_limit_post = 0;
static float mark_limit_v = 76;
static int mark_gear_change_post = 0;
static int mark_last_limit_change_post = 0;
static int last_limit = 0;
static int delta = 0; //added by hsg 2016-09-06

//static float last_post;

#define EN_CTR_THRESHOLD  1.8   //控制启动设定速度偏差
#define MIN_V_DIFF 5 //距离限速最小允许速度偏差
#define SET_BRAKE_GEAR  -15
#define SET_TRAC_GEAR  20
#define MARK_BRAKE_DISTANCE 1000
#define GET_THRESHOLD(a)((a)>60?3:5)
#define GET_MAXV_THRESHOLD(a)((a)>60?	opt_const.V_THRESHOLD:	opt_const.V_LIMIT_THRESHOLD)

RT_RESULT rt_controller(OPTSCHCURVE current_opt_result, RT_INPUT rt_in,
		int *gear_p, int over_speed_flag, int power_short_flag,
		OPTSCHCURVE ahead_opt_result) {

	int ahead_opt_gear = ahead_opt_result.gear;
	float rt_v = rt_in.rt_velocity;
	float opt_v = current_opt_result.velocity;
	int opt_gear = current_opt_result.gear;
	float con_tel_kp = current_opt_result.con_tel_kp;
	int road_len, crnt_end, next_type, next_len;
	int road_type = getRoadType(con_tel_kp+10, &road_len, &crnt_end, &next_type,
			&next_len); //根据公里标获得坡段的类型以及下一坡段的类型和长度


	//判断前方是否有制动
	if (ahead_opt_gear < 0) {
		exist_brake_flag = 1;
		brake_post = (int) rt_in.rt_con_tel_kp + MARK_BRAKE_DISTANCE;
	}

	if (exist_brake_flag == 1) {
		if (rt_in.rt_con_tel_kp >= brake_post) {
			exist_brake_flag = 0;
		}
	}

	//修正优化结果在特定范围
	if (opt_v
			> current_opt_result.limit_v
					- GET_THRESHOLD((int )current_opt_result.limit_v)) {
		opt_v = current_opt_result.limit_v
				- GET_THRESHOLD((int )current_opt_result.limit_v);
		if (opt_gear > opt_const.MAXGEAR) {
			opt_gear = opt_const.MAXGEAR;
		}
	} else {
		if (current_opt_result.limit_v >= 40) {
			if (opt_v < opt_const.MINV) {
				opt_v = opt_const.MINV;
			}
		}
	}

	//标记是否距离限速特近
	if (rt_v + GET_THRESHOLD((int )current_opt_result.limit_v)
			> (int) current_opt_result.limit_v) {
		very_close_to_limit_flag = 1;
	} else if (rt_v + GET_THRESHOLD((int )current_opt_result.limit_v) + 1
			<= (int) current_opt_result.limit_v) {
		very_close_to_limit_flag = 0;
	}

	//设定最大允许牵引和制动档位　//冗余，应该和优化代码共同限定该值，代码设置的局部静态变量太多-hsg
	if (current_opt_result.limit_v
			- GET_MAXV_THRESHOLD((int )current_opt_result.limit_v) < max_gear) {
		max_gear = current_opt_result.limit_v
				- GET_MAXV_THRESHOLD((int )current_opt_result.limit_v);
	} else {
		if (current_opt_result.con_tel_kp > mark_limit_post) {
			max_gear = current_opt_result.limit_v
					- GET_MAXV_THRESHOLD((int )current_opt_result.limit_v);
		}
	}
	if ((current_opt_result.limit_v > ahead_opt_result.limit_v)
			&& (max_gear
					> ahead_opt_result.limit_v
							- GET_MAXV_THRESHOLD((int )ahead_opt_result.limit_v))
			&& (ahead_opt_result.limit_v <= mark_limit_v)) {
		max_gear = ahead_opt_result.limit_v
				- GET_MAXV_THRESHOLD((int )ahead_opt_result.limit_v);
		mark_limit_v = ahead_opt_result.limit_v;
		mark_limit_post = ahead_opt_result.con_tel_kp;
	}
	if (max_gear > opt_const.MAXGEAR) {
		max_gear = opt_const.MAXGEAR;
	}
	if (very_close_to_limit_flag == 1 || over_speed_flag == 1) {
		// min_gear = opt_const.MINGEAR - 20; //增加允许的制动力
		min_gear = opt_const.MINGEAR + 20;//增加允许的制动力 20180124
	}
	
	// if (min_gear < -120) {
	// 	min_gear = -120;
	// }
	
	if (min_gear > -10) {min_gear = -10; }        //20180124
	

	//当无需启动控制调整时 //很多临界小数都是怎么定的？-hsg
	int gear = opt_gear;
	if (road_type == split_type_flag) { //分相区确保无档位输出
		gear = 0;
	}

	if (control_enable_flag == 0 && fabs(rt_v - opt_v) >= EN_CTR_THRESHOLD) {
		control_enable_flag = 1;
	}
	if (control_enable_flag == 0 && rt_v < opt_v - 0.8 && opt_gear < 0) {
		control_enable_flag = 1;
	}
	//将control_enable_flag置回0,下一循环起不再调整优化档位
	if (fabs(rt_v - opt_v) <= 0.5) {
		control_enable_flag = 0;
	}

	//设定state_flag，避免阈值引起抖动
	if (fabs(rt_v - opt_v) > 5) {
		state_flag = 1;
	} else if (fabs(rt_v - opt_v) < 3) {
		state_flag = 0;
	}

	//记录count
	count = count + 1;

//	if (rt_in.rt_con_tel_kp > 521668.531250) {
//		printf("test\n");
//	}

	//无需启动控制调整，但需保证档位不要跳变
	if (control_enable_flag == 0) {
		if (gear > rt_v) {
			if (last_gear < rt_v) {
				gear = rt_v;
			} else {
				if (count == 2 && last_gear < opt_gear) {
					gear = last_gear + 1;
				}
			}
		}
	}

	//需启用控制调整
	if (control_enable_flag == 1) {
		if (road_type == 2 || road_type == 0) { //上坡或缓坡情况，少用制动
			if (over_speed_flag == 1 || very_close_to_limit_flag == 1) { //有超速风险，直接启用制动逼近优化速度
				if (rt_v > opt_v) { //实速>优速
					if (gear > 0) {
						if ((rt_v - opt_v) > 0.5) {
							gear = SET_BRAKE_GEAR * (int) (rt_v - opt_v + 0.5);
						} else {
							gear = last_gear;
						}
					} else { //gear <= 0
						if ((rt_v - opt_v) > 0.5) {
							// gear = gear + SET_BRAKE_GEAR* (int) (rt_v - opt_v + 0.5);
							
							gear = gear - SET_BRAKE_GEAR* (int) (rt_v - opt_v + 0.5);//20180124
							if(gear>-10) gear = -10;//20180124
				

						} else {
							gear = last_gear;
						}
					}
				} else { //实速<优速，该情况应该不会出现，以防万一
					if (gear > 0) {
						gear = SET_BRAKE_GEAR * 5;
					} else { //gear <= 0
						
						// gear = gear + SET_BRAKE_GEAR * 5;

						gear = gear - SET_BRAKE_GEAR * 5;//增大制动力 20180124
						if(gear>-10) gear = -10;//增大制动力 20180124

					}
				}
			} else { //无超速风险等
				if (rt_v > opt_v) { //实速>优速
					if (last_gear > 0) {
						if (last_gear > (int) rt_v && count == 2) {
							gear = last_gear + SET_BRAKE_GEAR;
						} else if (gear <= (int) rt_v) {
							gear = 0;
						} else {
							gear = last_gear;
						}
					} else { //last_gear <= 0
						if (gear > 0) { //将正档位置0,负档位不变
							gear = 0;
						}
						if (state_flag == 1 && next_type == -2) {
							int tmp_delta_gear = SET_BRAKE_GEAR*(int)((rt_v - opt_v)/2);
							if (tmp_delta_gear > -10) {
								tmp_delta_gear = -10;
							}
							//gear = gear + tmp_delta_gear;
							gear = gear - tmp_delta_gear;//20180124
							if(gear>-10) gear = -10;//20180124
						}
					}
				} else { //实速<优速
					if (last_gear >= 0) {
						if (opt_gear >= 0) {
							if (count == 2 && last_gear < opt_const.MAXGEAR
									&& last_gear 	< (int) (rt_v + SET_TRAC_GEAR)) {
								gear = last_gear + 1;
							} else {
								gear = last_gear;
								if (gear < (int) rt_v) {
									gear = (int) rt_v;
								}
							}
						} else {
							gear = 0;
						}
					} else { //last_gear < 0，基本不会出现
						gear = 0;
					}
				}
			}
		} else if (road_type == -2) { //陡下坡情况
			if (over_speed_flag == 1 || very_close_to_limit_flag == 1) { //有超速风险，直接启用制动逼近优化速度
				if (rt_v > opt_v) { //实速>优速
					if (gear > 0) {
						if ((rt_v - opt_v) > 0.2) { //modified by hsg(0.5->0.2)
							gear = SET_BRAKE_GEAR * (int) (rt_v - opt_v + 1.0); //0.5->1.0
						} else {
							gear = last_gear;
						}
					} else { //gear <= 0
						if ((rt_v - opt_v) > 0.2) { //modified by hsg(0.5->0.2)
							// gear = gear + SET_BRAKE_GEAR * (int) (rt_v - opt_v + 1.0); // 0.5->1.0
							
							gear = gear - SET_BRAKE_GEAR * (int) (rt_v - opt_v + 1.0); // 20180124
							if(gear>-10) gear = -10;//20180124

						} else {
							gear = last_gear;
						}
					}
				} else { //实速<优速，该情况应该不会出现，以防万一
					if (gear > 0) {
						gear = SET_BRAKE_GEAR * 5;
					} else { //gear <= 0
						// gear = gear + SET_BRAKE_GEAR * 5;
						gear = gear - SET_BRAKE_GEAR * 5;//20180124
						if(gear > -10) gear = -10;//20180124
					}
				}
			} else { //无超速风险等
				if (rt_v > opt_v) { //实速>优速
					if (last_gear > 0) {
						if (last_gear > (int) rt_v && count == 2) {
							gear = last_gear + SET_BRAKE_GEAR * 2;
						} else if (gear <= (int) rt_v) {
							gear = 0;
						} else {
							gear = last_gear;
						}
					} else { //last_gear <= 0
						if (gear > 0) { //将正档位置0,负档位不变
							gear = 0;
						}
						if (state_flag == 1) {
							// gear = gear + SET_BRAKE_GEAR * 2;
							
							gear = gear - SET_BRAKE_GEAR * 2;//20180124
							if(gear>-10) gear=10;//20180124
						}
					}
				} else { //实速<优速
					if (state_flag == 0) {
						gear = gear + (int) (opt_v - rt_v);
						if (gear < 0) { //负档位置0,正档位不变
							gear = 0;
						}
					} else {
						gear = gear + (int) (opt_v - rt_v);
						if (gear < 0) { //负档位置0,正档位不变
							gear = 0;
						} else {
							if (last_gear < (int) rt_v) {
								gear = (int) rt_v;
							}
							if (last_gear > (int) rt_v && exist_brake_flag == 0
									&& count == 2) {
								gear = last_gear + 1;
							} else {
								gear = last_gear;
							}
						}
					}
				}
			}
		}
	}

	//将count置回0
	if (count == 2) {
		count = 0;
	}
	//归整档位
	if (gear > max_gear) {
		gear = max_gear;
	}
	if (gear < min_gear) {
		gear = min_gear;
	}

	if ( last_limit != current_opt_result.limit_v){
		mark_last_limit_change_post = current_opt_result.con_tel_kp;
		last_limit = current_opt_result.limit_v;
	}

	//减少档位抖动
	if (ahead_opt_result.gear >= last_gear && gear < last_gear && (state_flag == 1 && (ahead_opt_result.limit_v < current_opt_result.limit_v))
			&& road_type != split_type_flag && very_close_to_limit_flag == 0
			&& over_speed_flag == 0) {
		gear = last_gear;
	}
	if ((ahead_opt_result.gear <= last_gear + 5 && ahead_opt_result.gear >= last_gear - 10) && (gear < last_gear && gear < ahead_opt_result.gear) && road_type != split_type_flag && very_close_to_limit_flag == 0
			&& over_speed_flag == 0) {
		gear = ahead_opt_result.gear;
	}
	if (ahead_opt_result.gear <= last_gear && gear > last_gear
			&& (rt_v > current_opt_result.limit_v - 12 || current_opt_result.con_tel_kp - mark_last_limit_change_post < 30) && power_short_flag == 0
			&& road_type != split_type_flag ) {
		gear = last_gear;
	}
	if (ahead_opt_result.gear > last_gear && gear > ahead_opt_result.gear
			&& road_type != split_type_flag) {
		gear = ahead_opt_result.gear;
	}

	//标记负档位变化超过10的点，并用于约束负档位每个档位持续工作距离不要小于200米
	if (gear < 0){
		if (con_tel_kp - mark_gear_change_post <= 200 && con_tel_kp - mark_gear_change_post > 0 &&
				very_close_to_limit_flag == 0  && over_speed_flag == 0){
			gear = last_gear;
		}

		if (abs(gear - last_gear) >= 10){
			mark_gear_change_post = con_tel_kp;
		}
	}

	//消除横档情况下的档位跳变问题（特别是临近上限速情况） added by hsg 2016-09-06
	if (current_opt_result.limit_v - gear < 8 && rt_v - gear > 1) { //进入条件：档位接近限速（差值小于8）＆＆实际速度比档位大１以上
		if (delta > -3)
			delta = delta - 1;
		gear = gear + delta;
	} else {
		delta = 0;
	}

	*gear_p = gear;
	last_gear = gear;
	return RT_SUCCESS;
}
