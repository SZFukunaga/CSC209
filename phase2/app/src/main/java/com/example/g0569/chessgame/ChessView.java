package com.example.g0569.chessgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.widget.Toast;

import com.example.g0569.R;
import com.example.g0569.base.GameView;
import com.example.g0569.chessgame.model.ChessGame;
import com.example.g0569.utils.Coordinate;

/** The ChessView for the autoChessGame. */
public class ChessView extends GameView implements ChessContract.View {
  private Bitmap background;
  private Bitmap inventory;
  private Bitmap button;
  private Bitmap l2npc; // TODO
  private float buttonX;
  private float buttonY;
  private float inventoryX;
  private float inventoryY;
  private Bitmap triangle;
  private float triangleX;
  private float triangleY;
  private Bitmap triangle2;
  private float triangle2X;
  private float triangle2Y;
  private Bitmap star;
  private float starX;
  private float starY;
  private Bitmap star2;
  private float star2X;
  private float star2Y;

  private ChessGame autoChessGame;
  private ChessContract.Presenter presenter;

  /**
   * Instantiates a new Chess view.
   *
   * @param context the context
   */
  public ChessView(Context context) {
    super(context);
    paint.setTextSize(40);
    thread = new Thread(this);
  }

  @Override
  public void surfaceCreated(SurfaceHolder holder) {
    super.surfaceCreated(holder);
    initBitmaps();
    if (thread.isAlive()) {
      thread.start();
    } else {
      thread = new Thread(this);
      thread.start();
    }
  }

  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    super.surfaceChanged(holder, format, width, height);
  }

  @Override
  public void surfaceDestroyed(SurfaceHolder holder) {
    super.surfaceDestroyed(holder);
  }

  private void initBitmaps() {
    background = BitmapFactory.decodeResource(getResources(), R.drawable.autochessboard);

    scalex = screenWidth / background.getWidth();
    scaley = screenHeight / background.getHeight();

    button = BitmapFactory.decodeResource(getResources(), R.drawable.start);
    button = Bitmap.createScaledBitmap(button, 150, 150, false);
    inventory = BitmapFactory.decodeResource(getResources(), R.drawable.iteminventory);
    inventory = Bitmap.createScaledBitmap(inventory, 200, 300, false);

    triangle = BitmapFactory.decodeResource(getResources(), R.drawable.triangle);
    triangle = Bitmap.createScaledBitmap(triangle, 80, 80, false);
    triangleX = inventoryX;
    triangleY = inventoryY;

    triangle2 = BitmapFactory.decodeResource(getResources(), R.drawable.triangle);
    triangle2 = Bitmap.createScaledBitmap(triangle, 80, 80, false);
    triangle2X = screenWidth * 0.6f;
    triangle2Y = screenHeight * 0.4f;

    star = BitmapFactory.decodeResource(getResources(), R.drawable.star);
    star = Bitmap.createScaledBitmap(star, 80, 80, false);
    starX = inventoryX + inventory.getWidth() * 0.5f;
    starY = inventoryY;

    star2 = BitmapFactory.decodeResource(getResources(), R.drawable.star);
    star2 = Bitmap.createScaledBitmap(star, 80, 80, false);
    star2X = screenWidth * 0.6f;
    star2Y = screenHeight * 0.65f;

    autoChessGame = activity.getGameManager().getCurrentGame();
    // TODO store the presenter rather than the game
  }

  public void drawButton() {
    buttonX = screenWidth * 0.9f;
    buttonY = screenHeight * 0.7f;
    canvas.drawBitmap(button, buttonX, buttonY, paint);
  }

  public void drawInventory() {
    inventoryX = screenWidth / 30;
    inventoryY = screenHeight * 0.7f;
    canvas.drawBitmap(inventory, inventoryX, inventoryY, paint);
  }

  public void drawStar(Coordinate coordinate) {
    canvas.drawBitmap(star, coordinate.getX(), coordinate.getY(), paint);
  }

  public void drawTriangle(Coordinate coordinate) {
    canvas.drawBitmap(triangle, coordinate.getX(), coordinate.getY(), paint);
  }

  // TODO Add two images for player and NPC. Maybe...

  //  public void drawPlayer(Coordinate coordinate) {
  //    canvas.drawBitmap(l2player, coordinate.getX(), coordinate.getY(), paint);
  //  }
  //
  //  public void drawNPC(Coordinate coordinate) {
  //    canvas.drawBitmap(l2npc, coordinate.getX(), coordinate.getY(), paint);
  //  }

  @Override
  public void initView() {
    drawButton();
    drawInventory();
  }

  @Override
  public void draw() {
    try {
      canvas = sfh.lockCanvas();
      canvas.drawColor(Color.BLACK);
      canvas.save();
      canvas.scale(scalex, scaley, 0, 0);
      canvas.drawBitmap(background, 0, 0, paint);
      canvas.restore();
      initView();
      canvas.drawBitmap(star, starX, starY, paint);
      canvas.drawBitmap(triangle, triangleX, triangleY, paint);
      canvas.drawBitmap(triangle2, triangle2X, triangle2Y, paint);
      canvas.drawBitmap(star2, star2X, star2Y, paint);
    } catch (Exception err) {
      err.printStackTrace();
    } finally {
      if (canvas != null) {
        sfh.unlockCanvasAndPost(canvas);
      }
    }
  }

  @Override
  public void run() {
    while (threadFlag) {
      long startTime = System.currentTimeMillis();
      draw();
      long endTime = System.currentTimeMillis();
      try {
        if (endTime - startTime < 400) Thread.sleep(400 - (endTime - startTime));
      } catch (InterruptedException err) {
        err.printStackTrace();
      }
    }
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN && event.getPointerCount() == 1) {
      float x = event.getX();
      float y = event.getY();
      //      For later design... TODO
      //      List inventory_data = new ArrayList();
      //      inventory_data.add(inventoryX);
      //      inventory_data.add(inventory.getWidth());
      System.out.println(String.valueOf(x) + " " + String.valueOf(y));
      if (x > buttonX
          && x < buttonX + button.getWidth()
          && y > buttonY
          && y < buttonY + button.getHeight()) {
        Toast.makeText(activity, "Start the game.", Toast.LENGTH_SHORT).show();
        // Call method to start the game.
        boolean result = autoChessGame.autoFight();
        if (result) {
          Toast.makeText(activity, "You win the game!", Toast.LENGTH_SHORT).show();
          autoChessGame.showStatistic(true); // Win and get 2 cards.
        } else {
          autoChessGame.showStatistic(false); // Lose and get 0 cards.
          Toast.makeText(activity, "You lose the game!", Toast.LENGTH_SHORT).show();
        }
      } else if (x > inventoryX
          && x < inventoryX + inventory.getWidth() * 0.5f
          && y > inventoryY
          && y < inventoryY + inventory.getHeight() * 0.3333f) {
        Toast.makeText(activity, "Triangle chess was chosen", Toast.LENGTH_SHORT).show();
        int chosenPlace = 0;
        Coordinate location = autoChessGame.placeChess(chosenPlace);
        triangleX = location.getX();
        triangleY = location.getY();
      } else if (x > inventoryX + inventory.getWidth() * 0.5f
          && x < inventoryX + inventory.getWidth()
          && y > inventoryY
          && y < inventoryY + inventory.getHeight() * 0.3333f) {
        Toast.makeText(activity, "Star chess was chosen", Toast.LENGTH_SHORT).show();
        int chosenPlace = 1;
        Coordinate location = autoChessGame.placeChess(chosenPlace);
        starX = location.getX();
        starY = location.getY();
      }
      return true;
    }
    return false;
  }

  @Override
  public void setPresenter(ChessContract.Presenter presenter) {
    this.presenter = presenter;
  }
}