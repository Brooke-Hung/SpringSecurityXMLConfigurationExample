package org.mybatis.generator.plugins;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

public class RenameJavaMapperPlugin extends PluginAdapter {
	
	private String searchString;
    private String replaceString;
    private Pattern pattern;

	public boolean validate(List<String> arg0) {
		searchString = properties.getProperty("searchString");
        replaceString = properties.getProperty("replaceString");
        pattern = Pattern.compile(searchString);

        boolean valid = searchString != null && replaceString != null;

        if (valid) {
            pattern = Pattern.compile(searchString);
        }

        return valid;
	}
	

	@Override
	public void initialized(IntrospectedTable introspectedTable) {		
		String oldType = introspectedTable.getMyBatis3JavaMapperType();
        Matcher matcher = pattern.matcher(oldType);
        oldType = matcher.replaceAll(replaceString);

        introspectedTable.setMyBatis3JavaMapperType(oldType);
	}

}
