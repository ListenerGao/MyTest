package com.listenergao.mytest.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import com.listenergao.mytest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * create on 2018/12/3
 * Activity转场动画示例
 *
 * @author ListenerGao
 */
public class OptionActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.btn_activity_option_one)
    Button btnActivityOptionOne;
    @BindView(R.id.btn_activity_option_two)
    Button btnActivityOptionTwo;
    @BindView(R.id.btn_activity_option_three)
    Button btnActivityOptionThree;
    @BindView(R.id.btn_activity_option_four)
    Button btnActivityOptionFour;
    @BindView(R.id.btn_activity_option_five)
    Button btnActivityOptionFive;
    private Bitmap mBitmap;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_option_layout;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        toolbar.setTitle("Activity转场动画示例");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mBitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();


    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.btn_activity_option_one, R.id.btn_activity_option_two,
            R.id.btn_activity_option_three, R.id.btn_activity_option_four,
            R.id.btn_activity_option_five})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_activity_option_one:
                startActivity(1);
                break;
            case R.id.btn_activity_option_two:
                startActivity(2);
                break;
            case R.id.btn_activity_option_three:
                startActivity(3);
                break;
            case R.id.btn_activity_option_four:
                startActivity(4);
                break;
            case R.id.btn_activity_option_five:
                startActivity(5);
                break;
            default:
                break;
        }
    }

    private void startActivity(int optionType) {
        Intent intent = new Intent(this, OptionOneActivity.class);
        Bundle option = getOptionType(optionType);
        if (option != null) {
            Log.d("gys", "option != null");
            ActivityCompat.startActivity(this, intent, option);
        } else {
            startActivity(intent);
        }

    }

    private Bundle getOptionType(int type) {
        switch (type) {
            case 1:
                return ActivityOptionsCompat.makeCustomAnimation(this,
                        R.anim.slide_in_right_1000,
                        R.anim.slide_out_left_1000)
                        .toBundle();
            case 2:
                return ActivityOptionsCompat.makeScaleUpAnimation(image,
                        image.getWidth() / 2,
                        image.getHeight() / 2,
                        0, 0)
                        .toBundle();
            case 3:
                return ActivityOptionsCompat.makeThumbnailScaleUpAnimation(image,
                        mBitmap,
                        0, 0)
                        .toBundle();
            case 4:
                return ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                        image,
                        "Shared Element")
                        .toBundle();
            case 5:
                return ActivityOptionsCompat.makeClipRevealAnimation(image,
                        image.getWidth() / 2,
                        image.getHeight() / 2,
                        0, 0)
                        .toBundle();
            default:
                return null;
        }
    }
}
