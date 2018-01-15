### TensorFlow Raspberry Pi Examples

该文件夹包含如何使用TensorFlow构建Raspberry Pi应用程序的示例。
### 例程
按照[tensorflow/contrib/makefile](https://github.com/tensorflow/tensorflow/tree/master/tensorflow/contrib/makefile)中的说明的Raspberry Pi部分编译一个包含核心TensorFlow代码的静态库。
- 安装libjpeg, 才能加载图像文件:
```
sudo apt-get install -y libjpeg-dev
```
- 下载example model
```
To download the example model you'll need, run these commands:
curl https://storage.googleapis.com/download.tensorflow.org/models/inception_dec_2015_stripped.zip \
-o /tmp/inception_dec_2015_stripped.zip
unzip /tmp/inception_dec_2015_stripped.zip \
-d tensorflow/contrib/pi_examples/label_image/data/
```
- 从TensorFlow源代码树的根中，运行`make -f tensorflow/contrib/pi_examples/ label_image/Makefile`来构建一个基本的例子。

### 使用
- 运行`tensorflow/contrib/pi_examples / label_image/gen/bin/ label_image`，尝试使用默认的Grace Hopper图像进行图像标注。 你应该输出几行，以“Military Uniform”显示为最高结果，就像这样：
```
I tensorflow/contrib/pi_examples/label_image/label_image.cc:384] Running model succeeded!
I tensorflow/contrib/pi_examples/label_image/label_image.cc:284] military uniform (866): 0.624293
I tensorflow/contrib/pi_examples/label_image/label_image.cc:284] suit (794): 0.0473981
I tensorflow/contrib/pi_examples/label_image/label_image.cc:284] academic gown (896): 0.0280926
I tensorflow/contrib/pi_examples/label_image/label_image.cc:284] bolo tie (940): 0.0156956
I tensorflow/contrib/pi_examples/label_image/label_image.cc:284] bearskin (849): 0.0143348
```
- 利用example model分类`/home/ls/cat1.jpg`文件
`tensorflow/contrib/pi_examples/label_image/gen/bin/label_image --image=/home/ls/cat1.jpg`
- 一旦你验证了它的工作，你可以提供您自己的图像--image = your_image.jpg，甚至您已经用TensorFlow for Poets教程自己编写的图形，使用--graph = your_graph.pb - 输入 = Mul：0 --output = final_result：0。
- 注意：这里用了训练好的模型，即TensorFlow中预先给定了训练好的参数集，训练图片库是ImageNet。也就是说，小车识别出的物体只能是图片库里包含的labels，也没有“学习”的过程。
- label_image.cc文件,定义iamge,graph,label.

### Camera Example
一旦你有一个简单的例子运行，你可以尝试一个更复杂的版本，从连接到Pi的相机读取帧。 您需要首先安装和设置相机模块。 该示例使用Video4Linux，因此您需要首先安装。 这里有一些我觉得需要设置的命令，我在这篇博文中发现了更多的信息：

```
sudo apt-get install libv4l-dev
```


### 命令行输出
```
cd tensorflow
import commands
b = commands.getoutput('tensorflow/contrib/pi_examples/label_image/gen/bin/label_image')

isinstance(b,str)

'只保留数字：filter(str.isdigit, b)'1420142'
只保留字母：filter(str.isalpha, b)'dadefad'
```