/*
 * MIT License
 *
 * Copyright (c) 2021 Fontany--Legall Brandon
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package xyz.brandonfl.firedeamon.fusion.api.DTO;

/**
 * Available service information. Note that not all data will be available at all time.
 *
 * @author brandonfontany-legall
 */
public class ServiceInformation {
  private Service service;
  private ControlState controlState;
  private Runtime runtime;

  public Service getService() {
    return service;
  }

  public ControlState getControlState() {
    return controlState;
  }

  public Runtime getRuntime() {
    return runtime;
  }

  /**
   * Main information for a service
   */
  public static class Service {
    private String name;
    private String displayName;
    private String description;
    private String startupType;
    private String logOnAs;

    public String getName() {
      return name;
    }

    public String getDisplayName() {
      return displayName;
    }

    public String getDescription() {
      return description;
    }

    public String getStartupType() {
      return startupType;
    }

    public String getLogOnAs() {
      return logOnAs;
    }
  }

  /**
   * Rights on a service
   */
  public static class MissingRights {
    private boolean cantView;
    private boolean cantEdit;
    private boolean cantDelete;

    public boolean isCantView() {
      return cantView;
    }

    public boolean isCantEdit() {
      return cantEdit;
    }

    public boolean isCantDelete() {
      return cantDelete;
    }
  }

  /**
   * Actions that cannot be performed on a service
   */
  public static class ImpossibleActions{
    private boolean maynotDelete;

    public boolean isMaynotDelete() {
      return maynotDelete;
    }
  }

  /**
   * Information on the status of the service
   */
  public static class ServiceStatus {
    private int code;
    private boolean running;
    private String status;
    private boolean acceptingStop;
    private boolean stopped;

    public int getCode() {
      return code;
    }

    public boolean isRunning() {
      return running;
    }

    public String getStatus() {
      return status;
    }

    public boolean isAcceptingStop() {
      return acceptingStop;
    }

    public boolean isStopped() {
      return stopped;
    }
  }

  /**
   * List of actions that can be done on the service
   */
  public static class PerformableActions {
    private boolean stoppable;
    private boolean restartable;
    private boolean startable;

    public boolean isStoppable() {
      return stoppable;
    }

    public boolean isRestartable() {
      return restartable;
    }

    public boolean isStartable() {
      return startable;
    }
  }

  /**
   * Main information on the management of the service (rights, status, possible actions)
   */
  public static class ControlState {
    private MissingRights missingRights;
    private ImpossibleActions impossibleActions;
    private ServiceStatus serviceStatus;
    private PerformableActions performableActions;

    public MissingRights getMissingRights() {
      return missingRights;
    }

    public ImpossibleActions getImpossibleActions() {
      return impossibleActions;
    }

    public ServiceStatus getServiceStatus() {
      return serviceStatus;
    }

    public PerformableActions getPerformableActions() {
      return performableActions;
    }
  }

  /**
   * Main system information of a service
   */
  public static class Runtime {
    private int memUseKB;
    private String processStatus;
    private int pid;
    private String pidInfo;
    private String memUseInfo;
    private String cpuInfo;

    public int getMemUseKB() {
      return memUseKB;
    }

    public String getProcessStatus() {
      return processStatus;
    }

    public int getPid() {
      return pid;
    }

    public String getPidInfo() {
      return pidInfo;
    }

    public String getMemUseInfo() {
      return memUseInfo;
    }

    public String getCpuInfo() {
      return cpuInfo;
    }
  }
}
