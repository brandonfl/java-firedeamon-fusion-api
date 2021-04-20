package xyz.brandonfl.firedeamon.fusion.api.DTO;

public class ServiceInformation {
  public Service service;
  public ControlState controlState;
  public Runtime runtime;

  public static class Service{
    public String name;
    public String displayName;
    public String description;
    public String startupType;
    public String logOnAs;
  }

  public static class MissingRights{
    public boolean cantView;
    public boolean cantEdit;
    public boolean cantDelete;
  }

  public static class ImpossibleActions{
    public boolean maynotDelete;
  }

  public static class ServiceStatus{
    public int code;
    public boolean running;
    public String status;
    public boolean acceptingStop;
    public boolean stopped;
  }

  public static class PerformableActions{
    public boolean stoppable;
    public boolean restartable;
    public boolean startable;
  }

  public static class ControlState{
    public MissingRights missingRights;
    public ImpossibleActions impossibleActions;
    public ServiceStatus serviceStatus;
    public PerformableActions performableActions;
  }

  public static class Runtime{
    public int memUseKB;
    public String processStatus;
    public int pid;
    public String pidInfo;
    public String memUseInfo;
    public String cpuInfo;
  }
}
