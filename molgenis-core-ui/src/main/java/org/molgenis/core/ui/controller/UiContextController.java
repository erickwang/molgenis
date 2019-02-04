package org.molgenis.core.ui.controller;

import static java.util.Objects.requireNonNull;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.molgenis.core.ui.cookiewall.CookieWallService;
import org.molgenis.settings.AppSettings;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/context")
public class UiContextController {

  public static final String LOGIN_HREF = "/login";
  public static final String HELP_LINK_JSON =
      "{label: 'Help', href: 'https://molgenis.gitbooks.io/molgenis/content/'}";

  private final AppSettings appSettings;
  private final CookieWallService cookieWallService;

  public UiContextController(AppSettings appSettings, CookieWallService cookieWallService) {
    this.appSettings = requireNonNull(appSettings);
    this.cookieWallService = requireNonNull(cookieWallService);
  }

  @ApiOperation(value = "Returns the ui context object", response = ResponseEntity.class)
  @ApiResponses({
    @ApiResponse(
        code = 200,
        message = "Returns object containing settings relevant for user interface ",
        response = ResponseEntity.class)
  })
  @GetMapping("/**")
  @ResponseBody
  public UiContextResponse getContext() {

    JsonObject menu =
        appSettings.getMenu() != null
            ? new JsonParser().parse(appSettings.getMenu()).getAsJsonObject()
            : new JsonObject();

    boolean authenticated =
        SecurityContextHolder.getContext().getAuthentication().isAuthenticated();

    boolean showCookieWall = cookieWallService.showCookieWall();

    return UiContextResponse.builder()
        .setMenu(menu)
        .setLogoNavBarHref(appSettings.getLogoNavBarHref())
        .setLogoTopHref(appSettings.getLogoTopHref())
        .setLogoTopMaxHeight(appSettings.getLogoTopMaxHeight())
        .setLoginHref(LOGIN_HREF)
        .setHelpLink(HELP_LINK_JSON)
        .setShowCookieWall(showCookieWall)
        .setAuthenticated(authenticated)
        .build();
  }
}
