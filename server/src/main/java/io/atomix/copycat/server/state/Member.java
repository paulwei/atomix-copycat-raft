/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package io.atomix.copycat.server.state;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.CatalystSerializable;
import io.atomix.catalyst.serializer.Serializer;
import io.atomix.catalyst.transport.Address;

/**
 * Cluster member.
 *
 * @author <a href="http://github.com/kuujo>Jordan Halterman</a>
 */
public class Member implements CatalystSerializable {
  private Address serverAddress;
  private Address clientAddress;

  Member() {
  }

  public Member(Address serverAddress, Address clientAddress) {
    this.serverAddress = serverAddress;
    this.clientAddress = clientAddress;
  }

  /**
   * Returns the member ID.
   *
   * @return The member ID.
   */
  public int id() {
    return hashCode();
  }

  /**
   * Returns the server address.
   *
   * @return The server address.
   */
  public Address serverAddress() {
    return serverAddress;
  }

  /**
   * Returns the client address.
   *
   * @return The client address.
   */
  public Address clientAddress() {
    return clientAddress;
  }

  @Override
  public void writeObject(BufferOutput<?> buffer, Serializer serializer) {
    serializer.writeObject(serverAddress, buffer);
    serializer.writeObject(clientAddress, buffer);
  }

  @Override
  public void readObject(BufferInput<?> buffer, Serializer serializer) {
    serverAddress = serializer.readObject(buffer);
    clientAddress = serializer.readObject(buffer);
  }

  @Override
  public int hashCode() {
    return serverAddress.hashCode();
  }

  @Override
  public boolean equals(Object object) {
    return object instanceof Member && ((Member) object).serverAddress().equals(serverAddress);
  }

  @Override
  public String toString() {
    return String.format("%s[serverAddress=%s, clientAddress=%s]", getClass().getSimpleName(), serverAddress, clientAddress);
  }

}