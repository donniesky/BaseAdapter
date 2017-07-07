# BaseAdapter [![](https://jitpack.io/v/donniesky/BaseAdapter.svg)](https://jitpack.io/#donniesky/BaseAdapter)  [![Build Status](https://travis-ci.org/donniesky/BaseAdapter.svg?branch=master)](https://travis-ci.org/donniesky/BaseAdapter)  [![CircleCI](https://circleci.com/gh/donniesky/BaseAdapter/tree/master.svg?style=svg)](https://circleci.com/gh/donniesky/BaseAdapter/tree/master)  [![Stories in Ready](https://badge.waffle.io/donniesky/BaseAdapter.png?label=ready&title=Ready)](https://waffle.io/donniesky/BaseAdapter?utm_source=badge)  [![API](https://img.shields.io/badge/API-14%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=14)

## Setup and usage
### installation

#### with gradle

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:
```GRADLE
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
Step 2. Add the dependency

Assuming you have installed jitpack provider:
```GRADLE
dependencies {
        compile 'com.github.donniesky:BaseAdapter:v0.0.2'
}
```

or just import `library` module to your project.

## Features
Proxy Pattern.

* No need change anything with your target adapter;
* Not destory target adapter position;
* Support dynamic add & remove;
* No dependencies code build order

### Usage

```JAVA

Walle walle = Walle.newBuilder()
                .enableHeader(true)
                .headerRes(R.layout.view_header)
                .enableFooter(true)
                .footerRes(R.layout.view_footer)
                .enableLoadMore(true)
                .loadmoreRes(R.layout.view_load_more)
                .addLoadMoreListener(this)
                .wrapperAdapter(adapter).build();
		
rv.setAdapter(walle.getWrapperAdapter());

adapter.setData(getdatas());

```

### Thanks
* baseAdapter [hongyangAndroid/baseAdapter](https://github.com/hongyangAndroid/baseAdapter)
* BaseRecyclerViewAdapterHelper [CymChad/BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)
* 更优雅的方式添加 Header 与 Footer [优雅的添加Header和Footer等](http://www.woaitqs.cc/android/2017/04/11/new-way-to-add-header-and-footer)
