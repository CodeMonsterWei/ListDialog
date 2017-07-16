
[點擊下載 aar 檔](https://drive.google.com/file/d/0BxLWenH1MuEeY2NJZXdJM3BwMk0/view?usp=sharing)
[ xuite 部落格](http://blog.xuite.net/kalian/code/519824158)

## 快速導引：

* [主要功能](#主要功能)
* [畫面堆疊](#畫面堆疊)
* [Method 介紹](#Method 介紹)
* [顯示類型](#顯示類型)
* [修改類型](#修改類型)
* [事件類型](#事件類型)
* [使用圖片](#使用圖片)
* [基本使用](#基本使用)
* [快速使用](#快速使用)
* [添加效果背景](#添加效果背景)
* [item 的點擊效果](#item 的點擊效果)

#### 模擬 Dialog 的方式在內部增加一個 ListView
#### ListView 的 BaseAdapter 的 View 則選用 TextView

## 主要功能：

```
    多選一型 Dialog
    能因應選項數量自動增加 Item
    選擇點擊 灰幕 時是否退出畫面
```

## 畫面堆疊：

```
    灰幕
        DialogView
            TextView - Title
            TextView - Content
            ListView
```

### Method 介紹

## 顯示類型：

```
    顯示Title文字： setTitleText(String text)
                          當沒有使用時,則不會顯示
    顯示Content文字： setContentText(String text)
                          當沒有使用時,則不會顯示
    顯示List文字： setItemContent(String[] staArr)
                 setItemContent(ArrayList<String> arrayList)
                 當沒有使用時,則不會顯示
```

## 修改類型：

```
    修改Dialog背景： setDialogBackground(int backgroundID)
    修改Dialog寬高： setDialogSize(int width, int height)
                   預設為 寬高的 5 分之 3
                   輸入 0 為預設值
    修改Title文字大小： setTitleTextSize(float size)
                     預設為 30
    修改Content文字大小： setContentTextSize(float size)
                       預設為 25
    修改Item背景： setItemBackground(int backgroundID)
    修改Item背景顏色： setItemBackgroundColor(int a, int r ,int g, int b)
                    setItemBackgroundColor(int ColorID)
                    當使用 setItemBackground() 則此兩個 method 無效
    修改ItemTextSize大小： setItemTextSize(float size)
                         預設為 20
```

## 事件類型：

```
    點擊Item事件： setOnItemClickListener(OnItemClickListener listener)
    取消背景退出事件： cancelTheBackgroundToExit()
    移除Dialog事件： dismiss()
```

### 使用圖片：

## 基本使用：

![使用圖片](http://a.share.photo.xuite.net/kalian/1a8ed73/20226000/1166825123_x.jpg)
![使用圖片](http://a.share.photo.xuite.net/kalian/1a8ed74/20226000/1166825124_x.jpg)

## 快速使用：

![使用圖片](http://a.share.photo.xuite.net/kalian/1a8edf1/20226000/1166826017_x.jpg)
![使用圖片](http://a.share.photo.xuite.net/kalian/1a8ed20/20226000/1166824272_x.jpg)

## 添加效果背景：

![使用圖片](http://a.share.photo.xuite.net/kalian/1a8edf2/20226000/1166826018_x.jpg)
![使用圖片](http://a.share.photo.xuite.net/kalian/1a8ed61/20226000/1166824849_x.jpg)

## item 的點擊效果：

![使用圖片](http://a.share.photo.xuite.net/kalian/1a8ed02/20226000/1166824754_x.jpg)
![使用圖片](http://a.share.photo.xuite.net/kalian/1a8edd9/20226000/1166825225_x.jpg)
![使用圖片](http://a.share.photo.xuite.net/kalian/1a8ed84/20226000/1166825908_x.jpg)
