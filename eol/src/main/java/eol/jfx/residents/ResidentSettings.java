package eol.jfx.residents;

public enum ResidentSettings {
  RESIDENT;

  // Food consumption on creation
  public int getProductionCost() { return 2; }

  // Food consumed when eating
  public int getEatCost() { return 1; }

  // How much food is used per tick
  public int getHungerSpeed() { return 10; }

  // When hunger is at this level or lower, the resident will eat
  public int eatTrigger() { return 50; }

  // Hunger restored by eating
  public int getEatValue() { return 50; }
}
