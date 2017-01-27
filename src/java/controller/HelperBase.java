package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class HelperBase {
    
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    
    public HelperBase (HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }
    
    protected abstract void copyFromSession(Object sessionHelper);
    
    public void addHelperToSession(String name, SessionData state) {
        if (request == null) {
            return;
        }
        if(SessionData.READ == state) {
            Object sessionObj = request.getSession().getAttribute(name);
            if(sessionObj != null) {
                copyFromSession(sessionObj);
            }
        }
        request.getSession().setAttribute(name, this);
    }
    
    static public void writeError(HttpServletRequest request, HttpServletResponse response, String title, Exception ex) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html>");
        out.println("  <head>");
        out.println("    <title>" + title + "</title>");
        out.println("  </head>");
        out.println("  <body>");
        out.println("    <h2>" + title + "</h2>");
        if (ex.getMessage() != null)
            out.println("      <h3>" + ex.getMessage() + "</h3>");
         if (ex.getCause() != null)
            out.println("      <h4>" + ex.getCause() + "</h4>");       
         StackTraceElement[] trace = ex.getStackTrace();
         if (trace != null && trace.length > 0)
             out.print("<pre>");
         ex.printStackTrace(out);
         out.print("</pre>");
         out.println("  </body>");
         out.println("</html>");
         out.close();
    }
    
}
