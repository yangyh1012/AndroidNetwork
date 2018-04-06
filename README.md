# Android网络请求框架

框架是基于Retrofit2的二次封装，因为网上都是千篇一律的RX+Retrofit的方式来构建网络，个人不是很喜欢RX，所以这个框架是一个纯Retrofit的框架，代码已经放在Github上了。

那么开始吧。

先来个目录：
![Android网络请求框架目录](https://upload-images.jianshu.io/upload_images/9357607-b20575591599cb32.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

##先简要说说每个类或者接口的作用吧

[TCNetworkString]()：定义一些常量，比如状态码，字符串操作，并弄一些加密方法等等。

[TCNetworkConfig]()：定义了网络请求的配置项，比如超时设置、线上线下开关等网络配置信息。

[TCNetworkServiceFactory]()：创建服务工厂，比如项目中用到多个URI的时候，需要使用这个。

[TCNetworkServiceFactoryInterface]()：在MainActivity中实现此接口，注册服务类。

[TCNetworkService]()：根据线上或者线下获取URI和版本号。

[TCNetworkServiceInterface]()：业务继承此接口，定义URI、版本号、参数和请求失败返回的统一处理。

[TCNetworkManageRequestInterface]()：定义请求参数，成功返回处理和失败返回处理。

[TCNetworkManageInterface]()：定义服务类、API类名、API方法名。

[TCNetworkManage]()：请求主类，获取参数、服务、方法等等发起请求，然后统一返回。

## 如何使用？
很简单，创建一个类ServiceDemo，继承TCNetworkService，然后实现TCNetworkServiceInterface接口，实现以下几个方法：

    offlineRequestBaseUrl()//测试URI
    onlineRequestBaseUrl()//正式URI
    offlineRequestVersion()//测试版本号
    onlineRequestVersion()//正式版本号
    baseUrl()//将URI和版本号合并组成URL
    mapWithExtraParmas(Map param)//参数拼接处理
    requestFailedCommonHandle(Map data)//请求失败统一处理

写好这个类之后呢，在MainActivity中实现TCNetworkServiceFactoryInterface这个接口，实现以下方法：

    serviceInfos()//服务信息，用来放置多个URI

这个方法里面：创建一个Map，然后定义个key，比如Service1，然后将刚才创建的ServiceDemo的路径放进去，比如com.shanshan.demo.ServiceDemo。具体代码如下：

![放置多个服务](https://upload-images.jianshu.io/upload_images/9357607-832f3dfdb5b3fdce.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

如果你项目中还有别的服务，需要接通别的URI，可以再创建个Service2，然后把这个信息放进serviceInfos()中就OK了。

放完之后，记得在MainActivity的onCreate方法中写上一句：

    TCNetworkServiceFactory.getInstance().dataSource = this;//很关键

    //线上线下切换
    TCNetworkConfig.getInstance().isOnline = false;

服务类就这么弄完了，一些小型的app一般来说只需要创建一个服务类就可以一劳永逸了，

那么，接下来是处理一般请求。

1、首先你创建一个API接口APITest，作用是用来放置方法和请求方式，比如：

![放置方法和请求方式](https://upload-images.jianshu.io/upload_images/9357607-d4e81480862935ea.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

了解Retrofit2和okhttp3的朋友应该知道这个是怎么回事，就不做介绍了。

这个接口只需要定义一次，之后所有的API都往这里放，当然如果你要定义多个这样的API接口也是没问题的。

2、然后你创建一个类NetworkDemo，继承TCNetworkManage，实现TCNetworkManageInterface接口，然后实现以下几个方法：

    serviceIdentifier()//将刚才定义的key，Service1放进去
    apiClassPath()//将刚才定义的APITest路径放进去
    apiMethodName()//将刚才APITest中定义的方法getResult3放进去

将请求参数和返回参数的key用static final String来定义，比如：
![请求参数和返回参数](https://upload-images.jianshu.io/upload_images/9357607-f52e75b60b7de5f2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

我看过很多的Android和Java人士，喜欢定义一个类（所谓的“model”）用来直接转化请求返回的数据，我认为这是model的一种滥用，它其实不属于model。更有甚者还把请求参数弄成一个model，你恶心别人可以，但是恶心不了我。

我们在这里定义这个NetworkDemo，将请求参数和返回数据的key，以及服务，API都紧密地联系在一起。

tip：记得每个请求都创建一个这样的类，它替代了所谓的“model”，但胜过所谓的“model”。

3、搞定NetworkDemo之后，在需要请求的类中，实现TCNetworkManageRequestInterface接口，然后实现三个方法

    paramsForRequest(TCNetworkManage manage)//放置请求参数
    requestDidSuccess(TCNetworkManage manage)//请求成功返回
    requestDidFailed(TCNetworkManage manage)//请求失败返回

这时候你需要定义一个私有变量，比如：

    public NetworkDemo manage1;
  
多个请求就定义多个NetworkDemo，在刚才的三个方法中，只需要使用if…else…做区分就可以了。比如：

![区分NetworkDemo](https://upload-images.jianshu.io/upload_images/9357607-aea58b952bcec196.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

4、接下来就是激动人心的时刻了，我们即将发起请求，具体做法如下：

    //1、初始化各项数据，做好准备
    manage1 = new NetworkDemo();//创建这个类的实例
    manage1.networkManageRequestInterface = this;//将请求对象放入manage1中

    //2、发起请求
    double k = manage1.loadData();
    
    //3、如果你想取消请求，还可以这么做
    manage1.cancelRequest(k);

这个网络框架的使用基本就是这些了。

本来打算讲解一下实现思路，但是感觉没必要，纯粹的使用是不需要看的，如果想看就自己研究。

## 框架的拓展

这里讲一下拓展，因为这个网络请求框架功能毕竟还是比较少的，所以可拓展性强，比如可以拓展：

    1、请求缓存类。
    2、请求日志类。

另外，你还可以在TCNetworkConfig类中放入其他配置项，比如：

    1、是否需要缓存，缓存超时时间。
    2、是否需要设置httpbody。

或者，你也可以在TCNetworkManage类中放入：

    1、拦截器，用来对网络请求的发起和结束进行拦截。
    2、缓存处理方案。

[最近在重整Android，百废待兴，每天都思考着如何让Android的体验更好一些，这是一项大工程，目标是让Android的体验超越iOS！]()

-------------------------------我是分割线-------------------------------

# 闪闪金服 - 杨毅辉 
# 2018-04-04
