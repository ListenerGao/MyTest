package com.listenergao.mytest.requestBean;

import java.util.List;

/**
 * Created by ListenerGao on 2016/7/7.
 */
public class NewsMsgBean {

    /**
     * date : 20160707
     * stories : [{"images":["http://pic4.zhimg.com/6730360f93e5527f91fccabfddf3e7c3.jpg"],"type":0,"id":8534128,"ga_prefix":"070716","title":"这个网站一天播完整季热门美剧，用户看得爽，它也赚了钱"},{"images":["http://pic4.zhimg.com/34a1d2c8f640aa8b6d8d0d58f276b993.jpg"],"type":0,"id":8501390,"ga_prefix":"070715","title":"把一个圆柱形保温壶放进深海 3000 米，上岸后变成了这样"},{"images":["http://pic1.zhimg.com/7c470d727ed4ee698e8a32d0fb22ee70.jpg"],"type":0,"id":8535089,"ga_prefix":"070714","title":"「就是开不了口让她知道，就是那么简单几句我办不到」"},{"images":["http://pic4.zhimg.com/343d51ca24689ea9d488831e639599ff.jpg"],"type":0,"id":8536704,"ga_prefix":"070713","title":"除了拿小勺舀，你还可以怎么吃西瓜？"},{"images":["http://pic3.zhimg.com/a1fa5aa03f2304ec8f27d47b1b9024ae.jpg"],"type":0,"id":8533149,"ga_prefix":"070712","title":"大误 · 小店打烊了，客官请回吧"},{"images":["http://pic3.zhimg.com/88671a477a6b36ce505f38304496698e.jpg"],"type":0,"id":8515270,"ga_prefix":"070711","title":"郭德纲是救了相声还是毁了相声？"},{"images":["http://pic2.zhimg.com/7c93d448a93f58ecbbe415d9fa249bcd.jpg"],"type":0,"id":8533349,"ga_prefix":"070710","title":"洪水预测要准，又要早，光有 54 座水文站还不够"},{"images":["http://pic2.zhimg.com/34f8f9f020689f1b03e1b77363b76451.jpg"],"type":0,"id":8532609,"ga_prefix":"070709","title":"要跟着他混好几年，当然得精挑细选"},{"images":["http://pic1.zhimg.com/43dd3f31c8ddfc1b7a333a930bf02e80.jpg"],"type":0,"id":8534402,"ga_prefix":"070708","title":"- 今晚床边放碗血，你吸你的，我睡我的，行么？\r\n- 我拒绝"},{"images":["http://pic1.zhimg.com/cc0698a613553d00ca380661807fd544.jpg"],"type":0,"id":8533667,"ga_prefix":"070707","title":"课本里说 36V 是安全电压，超过了就一定会造成触电吗？"},{"images":["http://pic4.zhimg.com/dd4b8df9e25ea044edd5d20649fc1ebf.jpg"],"type":0,"id":8520432,"ga_prefix":"070707","title":"穷人比富人更乐于助人吗？"},{"title":"我们带上手机和游戏，去街上捕捉了妙蛙种子和可达鸭","ga_prefix":"070707","images":["http://pic3.zhimg.com/c2334f7743f191210bb08468e77ee502.jpg"],"multipic":true,"type":0,"id":8533598},{"images":["http://pic3.zhimg.com/874e93926abdf5942ae42be069c255b2.jpg"],"type":0,"id":8535379,"ga_prefix":"070707","title":"读读日报 24 小时热门 TOP 5 · 我不是不能结婚，我是选择了不结婚"},{"images":["http://pic3.zhimg.com/d430bba3ce23feed348a73aa8c83ed76.jpg"],"type":0,"id":8518756,"ga_prefix":"070706","title":"瞎扯 · 往事不堪回首"}]
     * top_stories : [{"image":"http://pic1.zhimg.com/c86848fdc6cb92afd89463014f7f4b64.jpg","type":0,"id":8535089,"ga_prefix":"070714","title":"「就是开不了口让她知道，就是那么简单几句我办不到」"},{"image":"http://pic4.zhimg.com/5c4981db7e4b042609508067028aee27.jpg","type":0,"id":8534128,"ga_prefix":"070716","title":"这个网站一天播完整季热门美剧，用户看得爽，它也赚了钱"},{"image":"http://pic1.zhimg.com/c0b903d312ae412f58ae0db9f819ee08.jpg","type":0,"id":8515270,"ga_prefix":"070711","title":"郭德纲是救了相声还是毁了相声？"},{"image":"http://pic2.zhimg.com/3c4824f2cec47101cfd52713d8aeb571.jpg","type":0,"id":8533598,"ga_prefix":"070707","title":"我们带上手机和游戏，去街上捕捉了妙蛙种子和可达鸭"},{"image":"http://pic1.zhimg.com/9fb3a984fa866ec5cb13fb9cb44a2be4.jpg","type":0,"id":8520432,"ga_prefix":"070707","title":"穷人比富人更乐于助人吗？"}]
     */

    private String date;
    /**
     * images : ["http://pic4.zhimg.com/6730360f93e5527f91fccabfddf3e7c3.jpg"]
     * type : 0
     * id : 8534128
     * ga_prefix : 070716
     * title : 这个网站一天播完整季热门美剧，用户看得爽，它也赚了钱
     */

    private List<StoriesBean> stories;

    @Override
    public String toString() {
        return "NewsMsgBean{" +
                "date='" + date + '\'' +
                ", stories=" + stories +
                ", top_stories=" + top_stories +
                '}';
    }

    /**
     * image : http://pic1.zhimg.com/c86848fdc6cb92afd89463014f7f4b64.jpg
     * type : 0
     * id : 8535089
     * ga_prefix : 070714
     * title : 「就是开不了口让她知道，就是那么简单几句我办不到」
     */

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
        private int type;
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
