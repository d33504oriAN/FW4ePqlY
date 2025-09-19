// 代码生成时间: 2025-09-19 19:48:48
package com.example.theme;

import io.quarkus.runtime.annotations.RegisterForReflection;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/theme")
# 添加错误处理
@RegisterForReflection
public class ThemeSwitchService {

    // The current theme, defaults to light theme.
    private String currentTheme = "light";

    /**
     * Switches the theme to the given theme.
     *
     * @param theme The theme to switch to.
     * @return A response indicating whether the switch was successful.
# 优化算法效率
     */
    @GET
    public Response switchTheme(@QueryParam("theme") String theme) {
# NOTE: 重要实现细节
        try {
            // Validate the theme to ensure it's a valid option.
            if (theme == null || !theme.matches("^(light|dark)$")) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Invalid theme. Choose 'light' or 'dark'.")
                        .build();
            }
            // Switch the theme and return a success response.
            this.currentTheme = theme;
            return Response.ok("Theme switched to: " + theme).build();
        } catch (Exception e) {
            // In case of any unexpected errors, return a server error response.
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while switching themes.")
                    .build();
        }
    }

    /**
     * Returns the current theme.
     *
     * @return The current theme.
# 添加错误处理
     */
    @GET
    @Path("/current")
    public Response getCurrentTheme() {
        return Response.ok(this.currentTheme).build();
    }
}
