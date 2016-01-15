package com.elminster.retrieve.xbox.data.game;

/**
 * The platform enum.
 * 
 * @author jgu
 * @version 1.0
 */
public enum Platform {

  UNKNOWN("Unknown"),
  WIN("Win"),
  XBOX("XBox"),
  ARCADE("Arcade"),
  X360("XBox 360"),
  XONE("XBox One");
  
  private final String name;
  
  Platform(String name) {
    this.name = name;
  }
  
  public String getName() {
    return name;
  }
  
  public static Platform getPlatformFromString(String platforStr) {
    switch (platforStr) {
      case "Xbox":
        return X360;
      case "XboxOne":
        return XONE;
      default:
        return UNKNOWN;
    }
  }
}
