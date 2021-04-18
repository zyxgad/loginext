
package com.github.zyxgad.loginext.handle;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.ArrayList;

import com.github.zyxgad.loginext.LoginExt;
import com.github.zyxgad.loginext.util.ErrorStackGetter;


public final class ConfigReader{
	private static final ConfigReader INSTANCE = new ConfigReader();

	public static ConfigReader getInstance(){
		return INSTANCE;
	}

	private ConfigReader(){}

	public String getJarPath(){
		return this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
	}

	public String[] readFileLines(String path){
		List<String> lines = new ArrayList<>();
		try(
			InputStream instream = this.getClass().getResourceAsStream(path);
			InputStreamReader reader = new InputStreamReader(instream);
			BufferedReader bufreader = new BufferedReader(reader, 256)
		){
			String line;
			while((line = bufreader.readLine()) != null){
				lines.add(line);
			}
		}catch(IOException e){
			LoginExt.LOGGER.severe("Read file line error:\n" + ErrorStackGetter.getErrorStack(e));
		}
		return lines.toArray(new String[lines.size()]);
	}
}
