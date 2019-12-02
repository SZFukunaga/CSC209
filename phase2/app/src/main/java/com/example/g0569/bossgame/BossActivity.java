package com.example.g0569.bossgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import com.example.g0569.R;
import com.example.g0569.base.BaseActivity;
import com.example.g0569.scoreboard.ScoreBoardActivity;
import com.example.g0569.utils.ActivityManager;
import com.example.g0569.utils.Constants;
import com.example.g0569.utils.Inventory;

/** The type Boss activity. */
public class BossActivity extends BaseActivity {

  private BossView bossView;
  private BossContract.Presenter presenter;
  private boolean isMenuVisible = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    this.getWindow()
        .setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    Bundle bundle = getIntent().getExtras();
    Inventory inventory = (Inventory) bundle.getSerializable(Constants.BUNDLE_INVENTORY_KEY);
    setContentView(R.layout.activity_bossgame);
    bossView = findViewById(R.id.bossview);
    presenter = new BossPresenter(bossView, inventory);

    Button menuBtn = findViewById(R.id.meny_btn);
  }

  @Override
  protected void onStart() {
    super.onStart();
    presenter.start();
  }

  public void toScoreBoard(){
    int bossScore = presenter.getScore();
    bundle.putInt(Constants.BUNDLE_BOSSSCORE_KEY, bossScore);
    Intent intent = new Intent(this, ScoreBoardActivity.class);
    intent.putExtras(bundle);
    ActivityManager.getInstance().finishAllActivity();
    startActivity(intent);
  }
}
