function tmpdrawCurves_online( )
%UNTITLED Summary of this function goes here
%   Detailed explanation goes here
% close all
% addpath ./route_data;
% addpath ./running_log;

% locoStart=650;
% locoEnd=766;
limitEnd = 1138207;
carLength = 500;
stations = load('./tmp_result/stations.txt'); %��վ��Ϣ
roadcategory = load('./tmp_result/tmp_rc_3002_45.txt'); %�¶���Ϣ
mGradient = load('./tmp_result/new_continue_gradients'); %�¶�ֵ��Ϣ 
startResult=load('./tmp_result/tmp_start_result_3002_45'); %��������
[start_row start_col] = size(startResult);
stopResult=load('./tmp_result/tmp_stop_result_3002_45'); %ͣ������
[stop_row stop_col] = size(stopResult);
pastResult=load('./tmp_result/tmp_opt_result_3002_45'); %��ʱ�Ż�����
[past_row past_col] = size(pastResult);
optimizeResult=load('./tmp_result/optimizeResult_3002'); %ԭʼ�Ż�����
[optimize_row optmize_col] = size(optimizeResult);




startX = pastResult(1,1) - 1500; %ԭʼ�Ż����ߵ���ʼ�����
endX = pastResult(past_row,1) + 1500; %ԭʼ�Ż����ߵ���ֹ�����

optimizeResultFirstIndex = 1;  %startX��ԭʼ�Ż������±�
optimizeResultSecondIndex = 1; %ԭʼ�Ż����ߺ���ʱ�Ż����ߵ��󽻵���ԭʼ�Ż������±�
optimizeResultThirdIndex = 1; %ԭʼ�Ż����ߺ���ʱ�Ż����ߵ��ҽ�����ԭʼ�Ż������±�
optimizeResultForthIndex = 1; %endX��ԭʼ�Ż������±�
stopResultIndex = 1; %ͣ����������ʱ�Ż����ߵĽ�������ʱ�Ż������±�
startResultIndex = 1; %����������ԭʼ�Ż����ߵĽ�����ԭʼ�Ż����ߵ��±�
airBreakIndex = 1; %ͣ�������п����ƶ���ʼ�±�

%�������index,��ʼ��ͼ�ĵط�
for j=1:optimize_row
    if  optimizeResult(j,1)>=startX
        optimizeResultFirstIndex=j;
        break;
    end
end

for j=1:optimize_row%��ʼ����ʱ�Ż����ߵĵط�
    if optimizeResult(j,1) >= pastResult(1,1)
        optimizeResultSecondIndex = j;
        break;
    end
end

for j=1:optimize_row
    if optimizeResult(j,1) >= pastResult(past_row,1)
        optimizeResultThirdIndex = j;
        break;
    end
end

for j=1:optimize_row
    if optimizeResult(j,1) >= endX
        optimizeResultForthIndex = j;
        break;
    end
end

for j=1:past_row
    if pastResult(j,1) >= stopResult(1,1)
        stopResultIndex = j;
        break;
    end
end

for j=1:optimize_row
    if optimizeResult(j,1) >= startResult(start_row,1)
        startResultIndex = j;
        break;
    end
end

airBreakIndex = 1;
for i = 1 : stop_row
    if stopResult(i,2) == -1050
        airBreakIndex = i;
        break
    end
end

%����ͨ������
finalStartIndex =  1;
finalLength = optimizeResultSecondIndex - optimizeResultFirstIndex;
finalEndIndex = finalStartIndex + finalLength - 1;
finalPastResult(finalStartIndex:finalEndIndex, :) = optimizeResult(optimizeResultFirstIndex:optimizeResultSecondIndex-1, :);

finalStartIndex = finalEndIndex + 1;
finalLength = past_row;
finalEndIndex = finalStartIndex + finalLength - 1;
finalPastResult(finalStartIndex:finalEndIndex, :) =  pastResult(1:past_row,:);

finalStartIndex = finalEndIndex + 1;
finalLength = optimizeResultForthIndex - optimizeResultThirdIndex;
finalEndIndex = finalStartIndex + finalLength - 1;
finalPastResult(finalStartIndex:finalEndIndex, :) =  optimizeResult(optimizeResultThirdIndex+1:optimizeResultForthIndex, :);

%����ͣ������
for i = 1 : stop_row
    if stopResult(i,2) == -1050
        stopResult(i,7) = 45;
        stopResult(i,2) = 0;
    end
end

finalStartIndex = 1;
finalLength = optimizeResultSecondIndex - optimizeResultFirstIndex;
finalEndIndex = finalStartIndex + finalLength - 1;
finalStopResult(finalStartIndex:finalEndIndex, :) = optimizeResult(optimizeResultFirstIndex:optimizeResultSecondIndex-1, :);

finalStartIndex = finalEndIndex + 1;
finalLength = stopResultIndex - 1;
finalEndIndex = finalStartIndex + finalLength - 1;
finalStopResult(finalStartIndex:finalEndIndex, :) =  pastResult(1:stopResultIndex-1,:);

finalStartIndex = finalEndIndex + 1;
finalLength = stop_row;
finalEndIndex = finalStartIndex + finalLength - 1;
finalStopResult(finalStartIndex:finalEndIndex, :) =  stopResult(1:stop_row,:);

%������������
finalStartIndex = 1;
finalEndIndex = start_row;
finalStartResult(finalStartIndex:finalEndIndex,:) = startResult(1:start_row,:);

finalStartIndex = finalEndIndex;
finalLength = optimizeResultForthIndex  - startResultIndex;
finalEndIndex = finalStartIndex + finalLength - 1;
finalStartResult(finalStartIndex:finalEndIndex,:) = optimizeResult(startResultIndex+1:optimizeResultForthIndex,:);

%--------------------------------------------������-------------------------------------------------------------------------------------
grid on;
[final_row, final_col] = size(finalPastResult);
for i = 1: final_row
    if finalPastResult(i,1) > limitEnd + carLength
        break;
    end
    if finalPastResult(i,1) > limitEnd
        finalPastResult(i,7) = 80;
    end
end
plot(finalPastResult(:,1),finalPastResult(:,7),'r','linewidth',2); %����
hold on;

%--------------------------------------------��ͨ������---------------------------------------------------------------------------------
plot(finalPastResult(:,1),finalPastResult(:,3),'g','linewidth',2); %��ʱ���ٲ��ֵ��ٶ�
hold on;
plot(finalPastResult(:,1),finalPastResult(:,2),'y','linewidth',2); %��λ
hold on;
xlim([startX, endX]);
ylim([-20,90]);
% --------------------------------------------ͨ�����߽���-------------------------------------------------------------------------------

%���¶�
index1 = 0;
startIndex=1;
[row col] = size(roadcategory);
endIndex=row;
%�޸���ʱ�¶�ֵ
roadcategory(1,1) = startX;
roadcategory(row,2) = endX;

startS = 1;
endS = length(mGradient);
[row col] = size(mGradient);
for j=1:row
    if startX>=mGradient(j,1) && startX<=mGradient(j,2)
        startS=mGradient(j,1);           %�������ճ�վ�ڵ���ʼ�����
    end
    if endX>=mGradient(j,1) && endX<=mGradient(j,2)
        endS=mGradient(j,2);
    end
end

startIndex=1;
flagS=0;
endIndex=length(mGradient);
points=zeros(1,1);
num=0;
for j=1:length(mGradient)
    if startX>mGradient(j,1)  && mGradient(j,2)>=startX
        if flagS==0
            flagS=1;
            startIndex=j;
        end
    end
    if endX>=mGradient(j,1) && endX<=mGradient(j,2)
        endIndex=j;
        break;
    end
end

index1 = 1;
point(index1, 3)=-95;
for k2=startIndex: endIndex
    index1 = index1 + 1;
    point(index1, 1) = mGradient(k2, 1);    %������վ�ڣ�����귶Χ��mGradient��Ч����
    point(index1, 2) = mGradient(k2, 2);
    point(index1, 3) = point(index1 - 1, 3)+(point(index1, 2)-point(index1, 1)).*mGradient(k2, 3)./1000;
end
x=point(:,2);
y=(point(:,3)+30)/10.0;
%plot(x,y,'k--','linewidth',2);    %���¶�����

% ����վλ��(����+Բ��)
hold on
r = 5;
for i=1 : length(stations)
    p(1,1)=1138207;
    p(1,2)=-80;
    p(2,1)=1138207;
    p(2,2)=10;
    plot(p(:,1),p(:,2),'color','b','linewidth',2);
    plot(p(1,1),  p(2,2),'bo','linewidth',10)  
end

grid on;

end
