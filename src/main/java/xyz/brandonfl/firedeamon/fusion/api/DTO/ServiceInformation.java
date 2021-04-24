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
