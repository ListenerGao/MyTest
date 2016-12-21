package com.listenergao.mytest.requestBean;

import java.util.List;

/**
 * Created by ListenerGao on 2016/11/28.
 * <p>
 * 最新消息的数据bean
 */

public class LatestMsgBean {

    @Override
    public String toString() {
        return "LatestMsgBean{" +
                "date='" + date + '\'' +
                ", stories=" + stories +
                ", top_stories=" + top_stories +
                '}';
    }

    /**
     * date : 20161128
     * stories : [{"images":["http://pic4.zhimg.com/724bbc43d5b33a41b81932ebe42a6477.jpg"],"type":0,"id":9010979,"ga_prefix":"112815","title":"孙悟空和印度的「神猴」有什么渊源？"},{"images":["http://pic4.zhimg.com/cbc71398acc13f00ff516c1ee71a1843.jpg"],"type":0,"id":9017180,"ga_prefix":"112814","title":"又出昏招，支付宝的新功能是谁出的主意？"},{"images":["http://pic2.zhimg.com/062654012faf742ff1939e17125c5645.jpg"],"type":0,"id":9017941,"ga_prefix":"112813","title":"这电池是好，但写成「充电几秒，续航一周」可就是忽悠了"},{"images":["http://pic2.zhimg.com/ac5c3d70ca6ab9a375225688fd9271d1.jpg"],"type":0,"id":9017935,"ga_prefix":"112812","title":"大误 · 睡觉不能太死啊"},{"images":["http://pic4.zhimg.com/452ef3d19868d17e987b4d1d2530c6e3.jpg"],"type":0,"id":9016870,"ga_prefix":"112811","title":"要定义真正的「尖锐」，「空间分辨率」才是更本质的叙述"},{"images":["http://pic4.zhimg.com/f5a52210676e207598cd750539629847.jpg"],"type":0,"id":9015541,"ga_prefix":"112810","title":"特朗普一直在抱怨，中国到底抢了美国多少就业岗位？"},{"images":["http://pic2.zhimg.com/4ab0f5aa857990245fc652804ee1cc05.jpg"],"type":0,"id":9013684,"ga_prefix":"112809","title":"变形金刚是很厉害，但不能说他们是「超级英雄」"},{"images":["http://pic3.zhimg.com/0fa64e9df46ff4b390af7c9bfe7d3fea.jpg"],"type":0,"id":9010027,"ga_prefix":"112808","title":"等我开会儿小差，就会更有灵感的"},{"images":["http://pic3.zhimg.com/e28298f6f2e9ce35ab294ee2c54489ea.jpg"],"type":0,"id":9015499,"ga_prefix":"112807","title":"一天会跑三个城市的电影路演，主创人员都要做什么？"},{"images":["http://pic3.zhimg.com/b9ee7b139aaff079fd371ddaae1490da.jpg"],"type":0,"id":9017011,"ga_prefix":"112807","title":"如何理解「北大四成新生认为人生没有意义」？"},{"images":["http://pic1.zhimg.com/0b3bd5e6a670f23857efcdfc209f57c0.jpg"],"type":0,"id":9016757,"ga_prefix":"112807","title":"别被「雾霾中发现耐药菌」吓到，先来看看原文怎么说"},{"images":["http://pic4.zhimg.com/51317ccbc2affe5db3232b7fa1b52eb3.jpg"],"type":0,"id":9017121,"ga_prefix":"112807","title":"读读日报 24 小时热门 TOP 5 · 支付宝的大尺度写真"},{"images":["http://pic2.zhimg.com/8cffef7fe39b9145f64554d9fc864d25.jpg"],"type":0,"id":9013816,"ga_prefix":"112806","title":"瞎扯 · 如何正确地吐槽"}]
     * top_stories : [{"image":"http://pic3.zhimg.com/c944dd1a47bed1df079245fe006cdcfe.jpg","type":0,"id":9017180,"ga_prefix":"112814","title":"又出昏招，支付宝的新功能是谁出的主意？"},{"image":"http://pic4.zhimg.com/f7401352edbbc7c4b08442a4623abb87.jpg","type":0,"id":9017941,"ga_prefix":"112813","title":"这电池是好，但写成「充电几秒，续航一周」可就是忽悠了"},{"image":"http://pic2.zhimg.com/3a49f65c08e83baf537de924b5c2c5d9.jpg","type":0,"id":9017011,"ga_prefix":"112807","title":"如何理解「北大四成新生认为人生没有意义」？"},{"image":"http://pic1.zhimg.com/4806e37fa963efa9f2dcdee5831be46c.jpg","type":0,"id":9015499,"ga_prefix":"112807","title":"一天会跑三个城市的电影路演，主创人员都要做什么？"},{"image":"http://pic2.zhimg.com/80ce9fe9226aa15a59367233a7803991.jpg","type":0,"id":9017121,"ga_prefix":"112807","title":"读读日报 24 小时热门 TOP 5 · 支付宝的大尺度写真"}]
     */

    private String date;
    private List<StoriesBean> stories;
    private List<TopStoriesBean> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public List<TopStoriesBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }

    public static class StoriesBean {
        /**
         * images : ["http://pic4.zhimg.com/724bbc43d5b33a41b81932ebe42a6477.jpg"]
         * type : 0
         * id : 9010979
         * ga_prefix : 112815
         * title : 孙悟空和印度的「神猴」有什么渊源？
         */

        private int type;

        @Override
        public String toString() {
            return "StoriesBean{" +
                    "type=" + type +
                    ", id=" + id +
                    ", ga_prefix='" + ga_prefix + '\'' +
                    ", title='" + title + '\'' +
                    ", images=" + images +
                    '}';
        }

        private int id;
        private String ga_prefix;
        private String title;
        private List<String> images;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }

    public static class TopStoriesBean {
        @Override
        public String toString() {
            return "TopStoriesBean{" +
                    "image='" + image + '\'' +
                    ", type=" + type +
                    ", id=" + id +
                    ", ga_prefix='" + ga_prefix + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }

        /**
         * image : http://pic3.zhimg.com/c944dd1a47bed1df079245fe006cdcfe.jpg
         * type : 0
         * id : 9017180
         * ga_prefix : 112814
         * title : 又出昏招，支付宝的新功能是谁出的主意？
         */

        private String image;
        private int type;
        private int id;
        private String ga_prefix;
        private String title;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
