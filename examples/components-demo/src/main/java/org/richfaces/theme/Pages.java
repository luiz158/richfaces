/**
 *
 */
package org.richfaces.theme;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * @author leo
 *
 */
public class Pages {
    public static final String DEFAULT_TITLE_PATTERN = "<ui\\:param\\s+name=\"title\"\\s+value=\"([^\"]*)\"";
    private static final Pattern XHTML_PATTERN = Pattern.compile(".*\\.xhtml");
    private static final Pattern FOLDER_PATTERN = Pattern.compile("(/.*/.*/).*\\.xhtml");
    private static final String EXAMPLE_PATH = "/examples";
    private Pattern titlePattern = compilePattern(DEFAULT_TITLE_PATTERN);
    private Map<String, List<PageDescriptionBean>> pageFolderMap;
    private List<PageDescriptionBean> indexPages;

    public Pattern compilePattern(String titlePattern) {
        return Pattern.compile(titlePattern, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    }

    @PostConstruct
    public void init() {
        pageFolderMap = new HashMap<String, List<PageDescriptionBean>>();
        Set<String> resourcePaths = getExternalContext().getResourcePaths(EXAMPLE_PATH);
        indexPages = new ArrayList<PageDescriptionBean>(resourcePaths.size());
        for (Iterator<String> iterator = resourcePaths.iterator(); iterator.hasNext();) {
            String folderPath = iterator.next();
            pageFolderMap.put(folderPath, getPagesByPattern(XHTML_PATTERN, folderPath));
            PageDescriptionBean folder = new PageDescriptionBean();
            folder.setPath(folderPath + "index.jsf");
            folder.setTitle(folderPath);
            indexPages.add(folder);
        }
    }

    private ExternalContext getExternalContext() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        ExternalContext externalContext = null;
        if (null != facesContext) {
            externalContext = facesContext.getExternalContext();
        }
        return externalContext;
    }

    public List<PageDescriptionBean> getXhtmlPages() {
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        Matcher m = FOLDER_PATTERN.matcher(viewId);
        String path;
        if (m.find()) {
            path = m.group(1);
        } else {
            return indexPages;
        }
        return pageFolderMap.get(path);
    }

    /**
     *
     */
    private List<PageDescriptionBean> getPagesByPattern(Pattern pattern, String path) {
        List<PageDescriptionBean> pageList = new ArrayList<PageDescriptionBean>();
        Set<String> resourcePaths = getExternalContext().getResourcePaths(path);
        for (Iterator<String> iterator = resourcePaths.iterator(); iterator.hasNext();) {
            String page = iterator.next();
            if (pattern.matcher(page).matches()) {
                PageDescriptionBean pageBean = new PageDescriptionBean();
                pageBean.setPath(page);
                InputStream pageInputStream = getExternalContext().getResourceAsStream(page);
                if (null != pageInputStream) {
                    byte[] head = new byte[1024];
                    try {
                        int readed = pageInputStream.read(head);
                        String headString = new String(head, 0, readed);
                        Matcher titleMatcher = titlePattern.matcher(headString);
                        if (titleMatcher.find() && titleMatcher.group(1).length() > 0) {
                            pageBean.setTitle(titleMatcher.group(1));
                        } else {
                            pageBean.setTitle(page);
                        }
                    } catch (IOException e) {
                        throw new FacesException("can't read directory content", e);
                    } finally {
                        try {
                            pageInputStream.close();
                        } catch (IOException e) {
                            // ignore it.
                        }
                    }
                }
                pageList.add(pageBean);
            }
        }
        Collections.sort(pageList);
        return pageList;
    }

    /**
     * @param titlePattern the titlePattern to set
     */
    public void setTitlePattern(String titlePattern) {
        this.titlePattern = compilePattern(titlePattern);
    }

    /**
     * @return the titlePattern
     */
    public String getTitlePattern() {
        return titlePattern.toString();
    }
}
