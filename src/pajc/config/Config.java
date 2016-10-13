package pajc.config;

public class Config {
	boolean production;
	public static String db_port;
	public static String db_name;
	public static String db_host;
	public static String db_user;
	public static String db_pass;
	public static String db_url;

	public Config(boolean production) {
		if (production) {
			this.production = true;
			Config.db_port = "3306";
			Config.db_name = "heroku_d7327337411b6c3";
			Config.db_host = "eu-cdbr-west-01.cleardb.com";
			Config.db_user = "b9afbe0d68e8ee";
			Config.db_pass = "6324b408";
			Config.db_url = "jdbc:mysql://" + db_host + "/" + db_name + "?autoReconnect=true&useSSL=false";
		} else {
			production = false;
			Config.db_port = "3306";
			Config.db_name = "square";
			Config.db_host = "localhost";

			Config.db_url = "jdbc:mysql://" + Config.db_host + ":" + Config.db_port + "/" + Config.db_name
					+ "?autoReconnect=true&useSSL=false";

			Config.db_url = "jdbc:mysql://" + Config.db_host + ":" + Config.db_port + "/" + Config.db_name
					+ "?autoReconnect=true&useSSL=false";
			Config.db_user = "root";
			Config.db_pass = "root";
		}
	}
}
