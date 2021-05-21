package Servers.Common;

import Common.Message;

public interface ServerProxy {

    public Message handleRequest(Message request);

}
