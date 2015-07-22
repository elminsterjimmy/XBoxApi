package com.elminster.retrieve.data.game;

/**
 * The platform enum.
 * 
 * @author jgu
 * @version 1.0
 */
public enum Platform {

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
}
