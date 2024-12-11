package eol.jfx.residents.works;

public class Unemployed extends Work {

  @Override
  public String toString() {
    return "Unemployed";
  }

  @Override
  public WorkType getWorkType() {
    return WorkType.UNEMPLOYED;
  }

  @Override
  public void generateRessources() {
    // No income
  }
}