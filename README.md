# ECornerImageView

# 效果
![圆角ImageView](https://upload-images.jianshu.io/upload_images/7019098-2d777318b0bc8b04.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![椭圆角ImageView](https://upload-images.jianshu.io/upload_images/7019098-1ec67c49cb9c1039.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![选择性椭圆角ImageView](https://upload-images.jianshu.io/upload_images/7019098-9dde71790a81d547.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 使用
1.依赖
````java
allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
````
````java
dependencies {
        implementation 'com.github.Mils-liu:ECornerImageView:v1.0.0'
}
````
2.布局  
cornerRadius为边角的半径，会覆盖cornerHeight和cornerWidth的值
````java
<com.mils.ecornerimageviewlibrary.view.ECornerImageView
        android:id="@+id/eimg"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:src="@drawable/img01"
        android:layout_margin="30dp"
        android:layout_centerInParent="true"
        android:scaleType="centerCrop"
        app:cornerHeight="80dp"
        app:cornerWidth="40dp"
        app:cornerRadius="40dp"/>
````
3.动态设置  
````java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eImg = (ECornerImageView)findViewById(R.id.eimg);
        //设置哪个角为椭圆角，分别为左上，左下，右上，右下
        eImg.setCorner(false,true,true,false);
        //设置圆角半径，单位为dp，会覆盖椭圆角的宽高
        eImg.setRadius(40);
        //设置椭圆角的高，单位为dp
        eImg.setcornerHeight(40);
        //设置椭圆角的宽，单位为dp
        eImg.setcornerWidth(80);
    }
````

# END
简书地址：https://www.jianshu.com/p/2b098d3f6643
