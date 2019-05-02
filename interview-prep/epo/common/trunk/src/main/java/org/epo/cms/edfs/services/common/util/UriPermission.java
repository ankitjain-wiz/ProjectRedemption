package org.epo.cms.edfs.services.common.util;

import java.util.HashMap;
import java.util.Map;

public enum UriPermission {
  NOTIFICATION("/notification", "notification_access"), EDOSSIERHISTORY("/dossier",
      "dossier_history_access"), SCAN_TRACKING_SHEET("/qrcode", "qrcode_access"), TABLEOFCONTENTS(
          "/TableOfContents", "toc_access"), EDFSSETTING("/settings", "setting_access");

  private String uri;
  private String permission;

  private static Map<String, UriPermission> map = new HashMap();

  static {
    for (UriPermission enumObj : UriPermission.values()) {
      map.put(enumObj.uri, enumObj);
    }
  }

   UriPermission(String uri, String permission) {
    this.uri = uri;
    this.permission = permission;
  }

  public String getUri() {
    return uri;
  }

  public String getPermission() {
    return permission;
  }

  public static UriPermission getEnumFromUri(String uri) {
    return map.get(uri);
  }

  @Override
  public String toString() {
    // TODO Auto-generated method stub
    return super.toString() + "(" + uri + ", " + permission + ")";
  }
}
