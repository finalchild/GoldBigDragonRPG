/*
 * Copyright 2015, 2016, 2017 GoldBigDragon
 * Copyright 2017 Final Child
 *
 * This file is part of GoldBigDragonRPG.
 *
 * GoldBigDragonRPG is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 2 of the License.
 *
 * GoldBigDragonRPG is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with GoldBigDragonRPG.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.goldbigdragon.goldbigdragonrpg.util;

import java.io.*;
import java.nio.charset.Charset;

import org.bukkit.plugin.java.JavaPlugin;

public class YamlController {
    private JavaPlugin plugin;

    public YamlController(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public YamlManager getNewConfig(String fileName, String[] header) {
        File file = this.getConfigFile(fileName);

        if (!file.exists())
            this.prepareFile(fileName);

        return new YamlManager(this.getConfigContent(fileName), file, this.getCommentsNum(file), plugin);
    }

    public boolean isExit(String fileName) {
        File file = this.getConfigFile(fileName);

        if (!file.exists()) {
            return false;
        } else return true;
    }

    public YamlManager getNewConfig(String fileName) {
        return this.getNewConfig(fileName, null);
    }

    private File getConfigFile(String file) {
        if (file.isEmpty() || file == null)
            return null;

        File configFile;

        if (file.contains("/")) {
            if (file.startsWith("/"))
                configFile = new File(plugin.getDataFolder() + file.replace("/", File.separator));
            else configFile = new File(plugin.getDataFolder() + File.separator + file.replace("/", File.separator));
        } else configFile = new File(plugin.getDataFolder(), file);

        return configFile;
    }

    public void prepareFile(String filePath, String resource) {
        File file = this.getConfigFile(filePath);

        if (file.exists())
            return;

        try {
            file.getParentFile().mkdirs();
            file.createNewFile();

            if (resource != null)
                if (!resource.isEmpty())
                    this.copyResource(plugin.getResource(resource), file);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void prepareFile(String filePath) {
        this.prepareFile(filePath, null);
    }

    public Reader getConfigContent(File file) {
        if (!file.exists())
            return null;
        try {
            StringBuilder whole = new StringBuilder();
            int commentNum = 0;
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String currentLine;
                while ((currentLine = reader.readLine()) != null) {
                    if (currentLine.startsWith("#")) {
                        whole.append(currentLine.replaceFirst("#", getPluginName() + "_COMMENT_" + commentNum + ":")).append("\n");
                        commentNum++;
                    } else {
                        whole.append(currentLine).append("\n");
                    }
                }
            }

            return new StringReader(whole.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private int getCommentsNum(File file) {
        if (!file.exists())
            return 0;
        try {
            int comments = 0;
            String currentLine;

            BufferedReader reader = new BufferedReader(new FileReader(file));

            while ((currentLine = reader.readLine()) != null)
                if (currentLine.startsWith("#"))
                    comments++;

            reader.close();
            return comments;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Reader getConfigContent(String filePath) {
        return getConfigContent(getConfigFile(filePath));
    }

    private String prepareConfigString(String configString) {
        int lastLine = 0;
        int headerLine = 0;

        String[] lines = configString.split("\n");
        StringBuilder config = new StringBuilder("");

        for (String line : lines) {
            if (line.startsWith(this.getPluginName() + "_COMMENT")) {
                String comment = "#" + line.trim().substring(line.indexOf(":") + 1);

                if (comment.startsWith("# +-")) {
                    if (headerLine == 0) {
                        config.append(comment + "\n");
                        lastLine = 0;
                        headerLine = 1;
                    } else if (headerLine == 1) {
                        config.append(comment + "\n\n");
                        lastLine = 0;
                        headerLine = 0;
                    }
                } else {
                    String normalComment;
                    if (comment.startsWith("# ' "))
                        normalComment = comment.substring(0, comment.length() - 1).replaceFirst("# ' ", "# ");
                    else normalComment = comment;

                    if (lastLine == 0)
                        config.append(normalComment + "\n");
                    else if (lastLine == 1)
                        config.append("\n" + normalComment + "\n");

                    lastLine = 0;
                }
            } else {
                config.append(line + "\n");
                lastLine = 1;
            }
        }
        return config.toString();
    }

    public void saveConfig(String configString, File file) {
        String configuration = this.prepareConfigString(configString);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(configuration);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPluginName() {
        return plugin.getDescription().getName();
    }

    private void copyResource(InputStream resource, File file) {
        try {
            OutputStream out = new FileOutputStream(file);

            int length;
            byte[] buf = new byte[1024];

            while ((length = resource.read(buf)) > 0)
                out.write(buf, 0, length);

            out.close();
            resource.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
