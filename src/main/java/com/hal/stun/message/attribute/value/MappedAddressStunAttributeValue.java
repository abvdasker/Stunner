package hal.com.stun.message.attribute.value;

public class MappedAddressAttributeValue {
  
  
  // 0x0001 - type;
  // 0x0000 - length;
  // value
  //   0x00 address family;
  //   0x0000 port;
  //   0x0000 0000 ipv4 address
  //     OR
  //   0x0000 0000 0000 0000 0000 0000 0000 0000 ipv6 address
  //
  private byte addressFamily;
  private short port;
  private String addressHex;
  
  public MappedAddressAttributeValue(String valueHex) {
    super(valueHex);
    parseValueHex(valueHex);
  }
  
  private parseValueHex(String valueHex) {
    
  }
  
}