/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mudraon.shared;

/**
 *
 * @author Mutagen
 */
public interface MessageApi {

    public String getAllMessage(Message _messageObj);

    public String getOnlyMessage(Message _messageObj);

    public String getOnlyDate(Message _messageObj);
}
