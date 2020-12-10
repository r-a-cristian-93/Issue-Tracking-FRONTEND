public class NavigationItemBean {
	private String name;
	private String url;
	
	public NavigationItemBean() {};
	
	public NavigationItemBean setName(String name) {
		this.name = name;
		return this;
	}
	public NavigationItemBean setUrl(String url) {
		this.url = url;
		return this;
	}
	public NavigationItemBean setState(String name, String url){
		this.name=name;
		this.url=url;
		return this;
	}
	public String getName(){
		return this.name;
	}
	public String getUrl(){
		return this.url;
	}
}
