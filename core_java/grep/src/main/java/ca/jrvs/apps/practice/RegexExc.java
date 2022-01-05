package ca.jrvs.apps.practice;

public interface RegexExc {

    /**
     * Returns True if filename extension is jpg or jpeg
     * Case insensitive
     * @param filename
     * @return boolean
     */
    public boolean matchJpeg(String filename);

    /**
     * Return True if IP is valie
     * IP valid range: 0.0.0.0 to 999.999.999.999
     * @param ip
     * @return boolean
     */
    public boolean matchIp(String ip);

    /**
     * Return True if line is empty (e.g. empty, white space, tabs)
     * @param line
     * @return boolean
     */
    public boolean isEmptyLine(String line);

}
