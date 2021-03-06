package org.molgenis.core.ui.style;

import static java.util.Objects.requireNonNull;
import static org.molgenis.core.ui.style.BootstrapVersion.BOOTSTRAP_VERSION_3;
import static org.molgenis.core.ui.style.BootstrapVersion.BOOTSTRAP_VERSION_4;
import static org.springframework.http.HttpStatus.OK;

import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.molgenis.core.ui.style.exception.GetThemeException;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class StyleController {
  private final StyleService styleService;

  public StyleController(StyleService styleService) {
    this.styleService = requireNonNull(styleService);
  }

  @GetMapping("/css/bootstrap-{bootstrap-version}/{theme}")
  @ResponseStatus(OK)
  public ResponseEntity getThemeCss(
      @PathVariable("bootstrap-version") String bootstrapVersion,
      @PathVariable("theme") String theme,
      HttpServletResponse response) {
    response.setHeader("Content-Type", "text/css");
    response.setHeader("Cache-Control", "max-age=31536000");

    final String themeName = theme.endsWith(".css") ? theme : theme + ".css";

    BootstrapVersion version =
        bootstrapVersion.equals("4") ? BOOTSTRAP_VERSION_4 : BOOTSTRAP_VERSION_3;
    Resource styleSheetResource = styleService.getThemeData(themeName, version);

    try (InputStream inputStream = styleSheetResource.getInputStream()) {
      IOUtils.copy(inputStream, response.getOutputStream());
      response.flushBuffer();
    } catch (IOException e) {
      throw new GetThemeException(theme, e);
    }

    return new ResponseEntity(HttpStatus.OK);
  }
}
