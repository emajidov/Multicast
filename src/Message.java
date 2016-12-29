import java.io.Serializable;

public class Message implements Serializable{
  String m; 
  int id;
  public Message(int id, String m){ 
	  this.id = id; 
	  this.m = m;
  }
}
