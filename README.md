# BaseAdapter [![](https://jitpack.io/v/donniesky/BaseAdapter.svg)](https://jitpack.io/#donniesky/BaseAdapter)  [![Build Status](https://travis-ci.org/donniesky/BaseAdapter.svg?branch=master)](https://travis-ci.org/donniesky/BaseAdapter)  [![CircleCI](https://circleci.com/gh/donniesky/BaseAdapter/tree/master.svg?style=svg)](https://circleci.com/gh/donniesky/BaseAdapter/tree/master)  [![Stories in Ready](https://badge.waffle.io/donniesky/BaseAdapter.png?label=ready&title=Ready)](https://waffle.io/donniesky/BaseAdapter?utm_source=badge)  [![API](https://img.shields.io/badge/API-14%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=14)  [![Build Status](https://app.bitrise.io/app/f694972ad785d484/status.svg?token=jZ87yfHg4hRc6990z_Rq2Q&branch=master)](https://www.bitrise.io/app/f694972ad785d484)

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
        compile 'com.github.donniesky:BaseAdapter:v0.1.1'
}
```

or just import `library` module to your project.

## Features

### BaseAdapter
> **BaseAdapter** allows you to shorten the code of most usual ```BaseAdapter```, taking care of implementing everything for you based on your data list. You only need to focus on the mapping between your view and your model.
> this library is to build your adapters by composing reusable components, 
> * Favor composition over inheritance .

### Usage

For single item adapter:

```JAVA
public class TestAdapter extends BaseAdapter<String, BaseViewHolder> {

    public TestAdapter() {
        super(R.layout.list_item);
    }

    @Override
    protected void convert(BaseViewHolder holder, String s, int position) {
        holder.setText(R.id.txt, s);
    }
}
```

For MultiItem adapter:

```JAVA
public class Test2Adapter extends MultiItemAdapter<String, BaseViewHolder> {
    public Test2Adapter() {
        super(null);

        addItemViewDelegate(new ItemDefaultDelegate());
        addItemViewDelegate(new Item1Delegate());
        addItemViewDelegate(new Item2Delegate());
    }
}
```
define an `ItemViewDelegate` for each view type. This delegate is responsible for creating ViewHolder and binding ViewHolder for a certain viewtype. An `ItemViewDelegate` get added to an `ItemViewDelegateManager`. This manager is the man in the middle between RecyclerView.Adapter and each `ItemViewDelegate`.

For example:

```JAVA
public class Item1Delegate implements ItemViewDelegate<String> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.view_item_1;
    }

    @Override
    public boolean isForViewType(String item, int position) {
        return item.equals("2");
    }

    @Override
    public void convert(BaseViewHolder holder, String s, int position) {
        holder.setText(R.id.txt1, s);
    }
}
```

### Walle
> Proxy Pattern.
> 
> * No need change anything with your target adapter;
> * Not destory target adapter position;
> * Support dynamic add & remove;
> * No dependencies code build order

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
