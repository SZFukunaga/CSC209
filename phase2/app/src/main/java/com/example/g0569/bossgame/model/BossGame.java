package com.example.g0569.bossgame.model;

import com.example.g0569.base.model.BaseGame;
import com.example.g0569.utils.NPC;
import java.util.List;

//TODO set classes to private or public

public class BossGame extends BaseGame {
  private BossPlayer bossPlayer;
  private Enemy enemy;
  //  MenuButton menuButton;
  //  PauseButton pauseButton;
  //  ShootButton shootButton;
  private HealthBar healthBar;
  private boolean paused;
  private List bossTeam;
  private int currentTeam;
  private NPC currentNPC;

  public BossGame() {
    super();
    paused = false;
  }

  /**
   * Instantiates everything needed for bossGame to run smoothly Makes a healthbar, enemy, and
   * player Might need to initialize current projectile upon creation
   */
  public void onStart() {

    bossPlayer = new BossPlayer(this);
    enemy = new Enemy(this);
    healthBar = new HealthBar(this, enemy);
  }

  /**
   * Returns the healthbar instance of the game. Should prob not need to use this later
   *
   * @return healthbar of the game
   */
  public HealthBar getHealthBar() {
    return healthBar;
  }

  //  public Enemy getEnemy() {
  //    return enemy;
  //  }

  //  public BossPlayer getBossPlayer() {
  //    return bossPlayer;
  //  }

  //  public void action() {}

  /** Pauses the game or unpauses the game depending on the state it is in */
  @Override
  public void pause() {
    paused = !paused;
  }

  /** Loads teh game after it has been saved */
  @Override
  public void load() {}

  /**
   * Checks to see if its within the range
   *
   * @param item_x of the item
   * @param item_y of the item
   * @param range_x of the item
   * @param range_y of the item
   * @param range_r of the item
   * @return true or false
   */
  private boolean inRange(float item_x, float item_y, float range_x, float range_y, float range_r) {
    return (item_x > range_x - range_r
        && item_x < range_x + range_r
        && item_y > range_y - range_r
        && item_y < range_y + range_r);
  }

  private boolean inRange(
      float item_x, float item_y, float range_x, float range_y, float range_dx, float range_dy) {
    return (item_x > range_x
        && item_x < range_x + range_dx
        && item_y > range_y
        && item_y < range_y + range_dy);
  }

  //  public void touch(float x, float y) {
  //    if (enemy.getHealth() > 0 && items >= 1) {
  //      if (inRange(x, y, shootButton.getX(), shootButton.getY(), shootButton.getR())) {
  //        Toast.makeText(getGameManager().getMainActivity(), "Throw!!!!",
  // Toast.LENGTH_SHORT).show();
  //        this.hit();
  //        // If we press the button on top is changes the color of the button
  //      } else if (inRange(x, y, 50, 50, getGameManager().getScreenWidth() / 20)) {
  //        shootButton.changeColor();
  //      } else if (inRange(
  //          x,
  //          y,
  //          pauseButton.getX(),
  //          pauseButton.getY(),
  //          pauseButton.getWidth(),
  //          pauseButton.getHeight())) {
  //        // Pauses the game if anywhere else is paused. Update later to include a pause button
  // for
  //        // all games
  //        pause();
  //      } else if (inRange(
  //          x,
  //          y,
  //          menuButton.getX(),
  //          menuButton.getY(),
  //          menuButton.getWidth(),
  //          menuButton.getHeight())) {
  //        List<String> statistic = new ArrayList<String>();
  //        getGameManager().showStatistic(statistic);
  //      }
  //    } else if (enemy.getHealth() <= 0) {
  //      List<String> statistic = new ArrayList<String>();
  //      statistic.add("YOU WON!!!");
  //      getGameManager().showStatistic(statistic);
  //    } else if (items < 1) {
  //      List<String> statistic = new ArrayList<String>();
  //      statistic.add("YOU LOSE!!!");
  //      getGameManager().showStatistic(statistic);
  //    }
  //  }

  /**
   * Attacks the boss and changes it's health accordingly Might want it to return something later so
   * that healthbar is not needed later
   */
  public void attackBoss() {
    enemy.attacked(currentNPC.getDamage(), currentNPC.getPower());
  }

  /** Throws the projectile, this logic should become useless later on */
  public void throwProjectile() {
    ThrownItems projectile = (ThrownItems) bossPlayer.getInventory().get(0);
    projectile.thrown();
  }

  /**
   * Switches the npc currently being used to attack the boss in the game Since it is rotational, if
   * it gets to the size then we reset to the first npc
   *
   * @return the npc that is next to attack
   */
  public NPC getNextProjectile() {
    if (bossTeam.size() != 0) {
      currentTeam++;
      if (currentTeam > bossTeam.size()) {
        currentTeam = 0;
      }
      currentNPC = (NPC) bossTeam.get(currentTeam);
    }
    return currentNPC;
  }

  /**
   * Allows the movement of the enemy to be set since it is dependent on the screen size of the
   * android device
   *
   * @param sizeOfScreen that the game is being played on.
   */
  public void setEnemyMovement(int sizeOfScreen) {
    enemy.setxDirection(sizeOfScreen);
  }

  /**
   * Gets the movement of the enemy to send back to the View so that the view knows how much to
   * change it by each time to enemy moves
   *
   * @return the amount the enemy should move.
   */
  public int getEnemyMovement() {
    return enemy.getXDirection();
  }

  /**
   * When initializing the presenter, we should get data that consists of what npcs we can choose to
   * attack with. We initalize it here and sets currentNPC to a default first in line npc
   *
   * @param team that we are entering the game with
   */
  public void setBossTeam(List team) {
    currentTeam = 0;
    this.bossTeam = team;
    if (bossTeam != null) currentNPC = (NPC) bossTeam.get(0);
  }

  /**
   * Allows us to know when the game has come to an end, which is dependent on the enemy's health
   *
   * @return whether or not the enemy is dead
   */
  public boolean determineEnd() {
    return enemy.getHealth() <= 0;
  }

  //  public NPC initNPC() {
  //    if (bossTeam != null){
  //    return bossTeam.get(currentTeam);
  //  }}
}
