# ArcView
Custom android view inspired from clean master

![preview](https://raw.githubusercontent.com/AswinpAshok/ArcView/master/Screenshots/preview.jpg)

To use this library, add

    compile 'com.aswin:CustomArc:1.0.2'
    
to your app level gradle file.
 

## Screenshots  
 
![](https://raw.githubusercontent.com/AswinpAshok/ArcView/master/Screenshots/screengif.gif) ![](https://raw.githubusercontent.com/AswinpAshok/ArcView/master/Screenshots/Screenshot2.png)

## XML Attributes (Referenceable in `app` namespace)
**`xmlns:app="http://schemas.android.com/apk/res-auto"`**
* `textSize` as dimension in `sp` unit : To specify text size. [Mandatory]
* `text` as String : To set progress. [**use numeric value only (0-100)**]
* `textColor` as color string : To specify text color (default white). 
* `arcWidth` as dimension in `dp` unit : To specify width of arc. [Mandatory]
* `arcActiveColor` as color string : To specify color of active portion of arc (default white).
* `arcInactiveColor` as color string : To specify color of inactive portion of arc (default whote having 50% opacity).
* `animatable` as boolean : To specify wheather to animate view or not. (default `true`)
* `progressInPercentage` as boolean : To specify wheather progress should show % sign or not (default false).

## Usage 
**xml**

    <com.aswin.CustomArc.ArcView
            android:layout_width="150dp"
            android:layout_height="150dp"
            android:id="@+id/myArc"
            app:text="65"
            app:textSize="35dp"
            app:textColor="#1971ff"
            app:arcWidth="15px"
            app:arcActiveColor="#1971ff"
            app:arcInactiveColor="#501971ff"
            app:animatable="true"
            app"progressInPercentage="true"/>
            
**Java**

    ArcView myArc;
    myArc=findViewById(R.id.myArc);
    
    myArc.setProgress(50); //This will override any text you set in xml file for this view.
    myArc.setAnimatable(false);//This will override app:animatable in xml for this view
