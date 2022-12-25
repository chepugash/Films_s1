package org.example.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.example.dao.FilmDao;
import org.example.entity.Film;
import org.example.util.DbException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@MultipartConfig
@WebServlet(urlPatterns = {"/images/*"})
public class FilmUploadImageServlet extends HttpServlet {
    private FilmDao filmDao;
    private int filmId;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        filmDao = (FilmDao) getServletContext().getAttribute("filmDao");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getPathInfo().substring(1);
        if (id == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Bad request. No id been provided");
        }
        try {
            String image = filmDao.getImage(Integer.parseInt(id));
            if (image == null) {
                image = "def.jpg";
            }
        File file = new File("..\\fileForImages\\Image\\" + image);
        resp.setHeader("Content-Type", id);
        resp.setHeader("Content-Length", String.valueOf(file.length()));
        resp.setHeader("Content-Disposition", "inline; filename=\"" + id + "\"");
        Files.copy(file.toPath().toAbsolutePath(), resp.getOutputStream());
        } catch (DbException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part filePart = req.getPart("file");
        filmId = Integer.parseInt((String) req.getSession().getAttribute("currentFilmId"));
        String fileName = "" + filmId + Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        InputStream inputStream = filePart.getInputStream();
        System.out.println("Single Filename:: " + fileName);
        new File("..\\fileForImages\\Image").mkdirs();
        System.out.println(System.getProperty("user.dir"));
        writeFile("..\\fileForImages\\Image\\" + fileName, inputStream);
        try {
            filmDao.setImageById(filmId, fileName);
        } catch (DbException e) {
            throw new ServletException(e);
        }
        resp.sendRedirect("/detail?id=" + filmId);
    }

    private void writeFile(String path, InputStream input) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(path)) {
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = input.read(bytes)) != -1) fileOutputStream.write(bytes, 0, read);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
