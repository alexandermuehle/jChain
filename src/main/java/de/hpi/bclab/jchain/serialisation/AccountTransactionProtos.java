package de.hpi.bclab.jchain.serialisation;

// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: accounttransaction.proto

public final class AccountTransactionProtos {
  private AccountTransactionProtos() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface TransactionOrBuilder extends
      // @@protoc_insertion_point(interface_extends:Transaction)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>required int32 receiver = 1;</code>
     */
    boolean hasReceiver();
    /**
     * <code>required int32 receiver = 1;</code>
     */
    int getReceiver();

    /**
     * <code>required int64 value = 2;</code>
     */
    boolean hasValue();
    /**
     * <code>required int64 value = 2;</code>
     */
    long getValue();
  }
  /**
   * Protobuf type {@code Transaction}
   */
  public static final class Transaction extends
      com.google.protobuf.GeneratedMessage implements
      // @@protoc_insertion_point(message_implements:Transaction)
      TransactionOrBuilder {
    // Use Transaction.newBuilder() to construct.
    private Transaction(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private Transaction(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final Transaction defaultInstance;
    public static Transaction getDefaultInstance() {
      return defaultInstance;
    }

    public Transaction getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private Transaction(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 8: {
              bitField0_ |= 0x00000001;
              receiver_ = input.readInt32();
              break;
            }
            case 16: {
              bitField0_ |= 0x00000002;
              value_ = input.readInt64();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return AccountTransactionProtos.internal_static_Transaction_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return AccountTransactionProtos.internal_static_Transaction_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              AccountTransactionProtos.Transaction.class, AccountTransactionProtos.Transaction.Builder.class);
    }

    public static com.google.protobuf.Parser<Transaction> PARSER =
        new com.google.protobuf.AbstractParser<Transaction>() {
      public Transaction parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new Transaction(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<Transaction> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    public static final int RECEIVER_FIELD_NUMBER = 1;
    private int receiver_;
    /**
     * <code>required int32 receiver = 1;</code>
     */
    public boolean hasReceiver() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required int32 receiver = 1;</code>
     */
    public int getReceiver() {
      return receiver_;
    }

    public static final int VALUE_FIELD_NUMBER = 2;
    private long value_;
    /**
     * <code>required int64 value = 2;</code>
     */
    public boolean hasValue() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>required int64 value = 2;</code>
     */
    public long getValue() {
      return value_;
    }

    private void initFields() {
      receiver_ = 0;
      value_ = 0L;
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      if (!hasReceiver()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasValue()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeInt32(1, receiver_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeInt64(2, value_);
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(1, receiver_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(2, value_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static AccountTransactionProtos.Transaction parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static AccountTransactionProtos.Transaction parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static AccountTransactionProtos.Transaction parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static AccountTransactionProtos.Transaction parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static AccountTransactionProtos.Transaction parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static AccountTransactionProtos.Transaction parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static AccountTransactionProtos.Transaction parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static AccountTransactionProtos.Transaction parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static AccountTransactionProtos.Transaction parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static AccountTransactionProtos.Transaction parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(AccountTransactionProtos.Transaction prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code Transaction}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:Transaction)
        AccountTransactionProtos.TransactionOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return AccountTransactionProtos.internal_static_Transaction_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return AccountTransactionProtos.internal_static_Transaction_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                AccountTransactionProtos.Transaction.class, AccountTransactionProtos.Transaction.Builder.class);
      }

      // Construct using AccountTransactionProtos.Transaction.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        receiver_ = 0;
        bitField0_ = (bitField0_ & ~0x00000001);
        value_ = 0L;
        bitField0_ = (bitField0_ & ~0x00000002);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return AccountTransactionProtos.internal_static_Transaction_descriptor;
      }

      public AccountTransactionProtos.Transaction getDefaultInstanceForType() {
        return AccountTransactionProtos.Transaction.getDefaultInstance();
      }

      public AccountTransactionProtos.Transaction build() {
        AccountTransactionProtos.Transaction result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public AccountTransactionProtos.Transaction buildPartial() {
        AccountTransactionProtos.Transaction result = new AccountTransactionProtos.Transaction(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.receiver_ = receiver_;
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        result.value_ = value_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof AccountTransactionProtos.Transaction) {
          return mergeFrom((AccountTransactionProtos.Transaction)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(AccountTransactionProtos.Transaction other) {
        if (other == AccountTransactionProtos.Transaction.getDefaultInstance()) return this;
        if (other.hasReceiver()) {
          setReceiver(other.getReceiver());
        }
        if (other.hasValue()) {
          setValue(other.getValue());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        if (!hasReceiver()) {
          
          return false;
        }
        if (!hasValue()) {
          
          return false;
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        AccountTransactionProtos.Transaction parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (AccountTransactionProtos.Transaction) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private int receiver_ ;
      /**
       * <code>required int32 receiver = 1;</code>
       */
      public boolean hasReceiver() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>required int32 receiver = 1;</code>
       */
      public int getReceiver() {
        return receiver_;
      }
      /**
       * <code>required int32 receiver = 1;</code>
       */
      public Builder setReceiver(int value) {
        bitField0_ |= 0x00000001;
        receiver_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required int32 receiver = 1;</code>
       */
      public Builder clearReceiver() {
        bitField0_ = (bitField0_ & ~0x00000001);
        receiver_ = 0;
        onChanged();
        return this;
      }

      private long value_ ;
      /**
       * <code>required int64 value = 2;</code>
       */
      public boolean hasValue() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <code>required int64 value = 2;</code>
       */
      public long getValue() {
        return value_;
      }
      /**
       * <code>required int64 value = 2;</code>
       */
      public Builder setValue(long value) {
        bitField0_ |= 0x00000002;
        value_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required int64 value = 2;</code>
       */
      public Builder clearValue() {
        bitField0_ = (bitField0_ & ~0x00000002);
        value_ = 0L;
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:Transaction)
    }

    static {
      defaultInstance = new Transaction(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:Transaction)
  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Transaction_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_Transaction_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\030accounttransaction.proto\".\n\013Transactio" +
      "n\022\020\n\010receiver\030\001 \002(\005\022\r\n\005value\030\002 \002(\003B\032B\030Ac" +
      "countTransactionProtos"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_Transaction_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_Transaction_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_Transaction_descriptor,
        new java.lang.String[] { "Receiver", "Value", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}