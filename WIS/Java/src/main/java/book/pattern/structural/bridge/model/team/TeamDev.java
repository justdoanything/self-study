package book.pattern.structural.bridge.model.team;

public class TeamDev implements Team {
    @Override
    public void applyTeam() {
        System.out.println("Dev Team");
    }
}
