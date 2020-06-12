package com.listenergao.mytest.activity;

import android.graphics.Color;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.listenergao.mytest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CollapsingToolbarLayoutActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_collapsing_toolbar_layout)
    CoordinatorLayout activityCollapsingToolbarLayout;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_collapsing_toolbar_layout;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        //设置状态栏为全透明。
        StatusBarUtil.setTransparent(this);

//        toolbar.setTitle("CollapsingToolbarLayout");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout.setTitle("CollapsingToolbarLayout");
        collapsingToolbarLayout.setExpandedTitleColor(Color.BLACK);     //设置展开时Title的颜色
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);    //设置收缩时Title的颜色

        tvContent.setText("新华社印度果阿10月17日电 题：携手规划金砖国家发展新蓝图——记习近平主席出席在印度果阿举行的金砖国家领导人第八次会晤\n" +
                "椰林摇曳，沙滩延绵。印度西海岸，阿拉伯海之畔。10月15日至16日，国家主席习近平出席在印度果阿举行的金砖国家领导人第八次会晤。从南非德班到巴西福塔莱萨，从俄罗斯乌法到印度果阿，这是习近平主席第4次出席金砖国家领导人会晤。\n" +
                "今年恰逢金砖国家合作10周年。十年磨一剑。金砖国家合作正面临拓展深化的重要任务。习近平主席出席峰会，发表重要讲话，同各方深入交流，高瞻远瞩、切中肯綮，为金砖发展把脉开方。\n" +
                "关键时刻，引领金砖发展新航程\n" +
                "当地时间15日下午1时许，习近平主席乘坐的专机抵达果阿海军机场。中国红、巴西绿、俄罗斯蓝、印度橙、南非黄，峰会会徽“五色莲花”，绽放在果阿大街小巷。\n" +
                "习近平主席出席峰会，世界高度关注；金砖发展的中国主张，各方充满期待。\n" +
                "一个多月前，金砖国家领导人聚首西子湖畔，在二十国集团领导人杭州峰会期间举行了非正式会晤。习近平主席重申开放、包容、合作、共赢的金砖精神。\n" +
                "“打造有效、包容、共同的解决方案”——果阿峰会主题契合着金砖精神的主旨。\n" +
                "泰姬酒店草坪上，巨大的圆形帐篷被“五色莲花”环绕簇拥。16日下午，金砖国家领导人大范围会议在这里举行。圆形会议桌旁，5国领导人神情专注，共商金砖发展大计。\n" +
                "内外因素综合影响，使金砖国家经济增速有所放缓，发展面临新挑战。\n" +
                "中国，善于转危为机，在挑战中迎难而上。\n" +
                "东道国印度总理莫迪致开场白后，首先邀请习近平主席讲话。\n" +
                "习近平主席发表了题为《坚定信心　共谋发展》的重要讲话。开阔的视野、详实的数据、精准的判断、辩证的思维，讲话为金砖国家面对复杂严峻外部环境、携手寻找应对之道，廓清方向、提振信心、注入动力。\n" +
                "回首过往，10年耕耘，10年收获，令人信心倍增——\n" +
                "这是共谋发展、不断前行的10年；这是拓展合作、互利共赢的10年；这是敢于担当、有所作为的10年。\n" +
                "展望未来，5个“共同”的思路建议，指明前进方向——\n" +
                "共同建设开放世界；共同勾画发展愿景；共同应对全球性挑战；共同维护公平正义；共同深化伙伴关系。\n" +
                "面向未来，搭建联通发展新舞台\n" +
                "16日下午，金砖国家领导人同“环孟加拉湾多领域经济技术合作倡议”成员国领导人举行对话会，聚焦新兴市场国家和发展中国家发展合作。\n" +
                "习近平主席说：“金砖国家和‘环孟加拉湾多领域经济技术合作倡议’成员国同属发展中国家大家庭，在维护世界和平、守卫地区安全方面有着共同愿望，在发展本国经济、改善民生福祉方面有着共同需求。”\n" +
                "与有关地区组织交流对话已成为金砖国家领导人会晤的“规定动作”。扩大和巩固金砖国家的“朋友圈”，推动南南合作，让更多发展中国家搭上发展的快车、便车，这是金砖国家的共同意愿，也是中国正确义利观的生动注解。\n" +
                "对话会上，习近平主席提出推动“一带一路”建设和“环孟加拉湾多领域经济技术合作倡议”有关规划有机对接、促进基础设施建设和互联互通等多项倡议，各方积极响应。发展战略对接、互联互通、“一带一路”成为对话会上的高频词。\n" +
                "“中方将始终把同发展中国家合作放在突出位置，不断为南南合作注入新动力，将秉持亲诚惠容的周边外交理念，不断为亚洲互利合作开辟新前景。”\n" +
                "果阿峰会上，中国携手周边，共建发展的新平台。\n" +
                "15日抵达果阿当晚，习近平主席会见尼泊尔总理普拉昌达。“南亚国家您已经基本走遍，相信尼泊尔也一定会成为您访问的目的地”，普拉昌达总理盛情邀请习近平主席访问尼泊尔。\n" +
                "习近平主席说，中国和尼泊尔是山水相连的亲密邻邦，中方愿同尼方一道，对接发展战略，深化各领域务实合作，打造中尼命运共同体。普拉昌达总理回应：“尼泊尔视中国为可靠的发展伙伴，愿同中国发展更加全面的伙伴关系。”\n" +
                "16日是最忙碌的一天。在连续出席12场多边活动后，习近平主席仍安排会见缅甸国务资政昂山素季和斯里兰卡总统西里塞纳。两个国家都是中国的好朋友、好邻居，是“一带一路”建设和发展合作的重要伙伴，对中国意义重大。\n" +
                "会见昂山素季，习近平主席特别强调，要弘扬“胞波”传统友谊，推动中缅全面战略合作伙伴关系不断向前发展。\n" +
                "会见西里塞纳，习近平主席谈的最多的是“一带一路”：“中方赞赏斯方支持并积极参与‘一带一路’建设，愿同斯方在‘一带一路’框架内深化务实合作。”\n" +
                "无论是同周边国家共商合作大计，还是在多边场合与各国共谋发展之道，习近平主席在峰会期间提出的倡议理念都同二十国集团领导人杭州峰会共识一脉相承、融会贯通，体现出中方推进全球经济治理、构建全球伙伴关系的信心和决心。\n" +
                "3年前，习近平主席先后提出“一带一路”倡议和亲诚惠容的周边外交理念。春华秋实，三年有成。高举和平发展的旗帜，古老的东方大国，正在一步一个脚印走向世界舞台的中央。\n" +
                "明年9月，金砖国家领导人第九次会晤将在东海之滨的福建厦门举行。中国，将同各国一道，携手努力，谱写金砖国家合作新篇章，开启建设美好世界的新征程。");


    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.fab)
    public void onClick(View view) {
        Snackbar snackbar = Snackbar.make(activityCollapsingToolbarLayout,"已收藏...",Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }
}
