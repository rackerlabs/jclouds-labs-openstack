/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.openstack.glance.v2_0.domain;

import static com.google.common.base.Preconditions.checkNotNull;

import java.beans.ConstructorProperties;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Named;

import org.jclouds.javax.annotation.Nullable;
import org.jclouds.openstack.v2_0.domain.Link;
import org.jclouds.openstack.v2_0.domain.Resource;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

/**
 * An image the Glance server knows about
 * 
 * @author Ignacio Mulas
 * 
 * @see <a href= "http://glance.openstack.org/glanceapi.html" />
 * @see <a href=
 *      "https://github.com/openstack/glance/blob/master/glance/api/v2/images.py"
 *      />
 */
public class Image extends Resource {

   public static final String IMAGE = "image", ID = "id", NAME = "name", VISIBILITY = "visibility",
         CHECKSUM = "checksum", MIN_DISK = "min_disk", MIN_RAM = "min_ram", IS_PUBLIC = "is_public",
         PROTECTED = "protected", CREATED_AT = "created_at", UPDATED_AT = "update_at", DELETED_AT = "deleted_at",
         OWNER = "owner", STATUS = "status", DISK_FORMAT = "disk_format", CONTAINER_FORMAT = "container_format",
         SIZE = "size", SIZE_MIN = "size_min", SIZE_MAX = "size_max", STORE = "store", PROPERTY = "property",
         TAGS = "tags";

   public static enum Status {

      UNRECOGNIZED, ACTIVE, SAVING, QUEUED, KILLED, PENDING_DELETE, DELETED;

      public String value() {
         return name();
      }

      public static Status fromValue(String v) {
         try {
            return valueOf(v.toUpperCase());
         } catch (IllegalArgumentException e) {
            return UNRECOGNIZED;
         }
      }
   }

   public static Builder<?> builder() {
      return new ConcreteBuilder();
   }

   public Builder<?> toBuilder() {
      return new ConcreteBuilder().fromImage(this);
   }

   public abstract static class Builder<T extends Builder<T>> extends Resource.Builder<T> {
      protected Long size;
      protected String checksum;
      protected Date updatedAt;
      protected Date createdAt;
      protected Image.Status status;
      protected String visibility;
      protected String self;
      protected String file;
      protected String schema;
      protected List<String> tags = ImmutableList.of();
      protected ContainerFormat containerFormat;
      protected DiskFormat diskFormat;
      protected boolean deleted;
      protected long minDisk;
      protected long minRam;
      protected String owner;
      protected Date deletedAt;
      protected boolean isPublic;
      protected ImmutableMap<String, String> properties = ImmutableMap.of();

      /**
       * @see Image#getStatus()
       */
      public T status(Image.Status status) {
         this.status = status;
         return self();
      }

      /**
       * @see Image#getSize()
       */
      public T size(Long size) {
         this.size = size;
         return self();
      }

      /**
       * @see Image#getChecksum()
       */
      public T checksum(String checksum) {
         this.checksum = checksum;
         return self();
      }

      /**
       * @see Image#getUpdatedAt()
       */
      public T updatedAt(Date updatedAt) {
         this.updatedAt = updatedAt;
         return self();
      }

      /**
       * @see Image#getCreatedAt()
       */
      public T createdAt(Date createdAt) {
         this.createdAt = createdAt;
         return self();
      }

      /**
       * @see Image#getSelf()
       */
      public T visibility(String visibility) {
         this.visibility = visibility;
         return self();
      }

      /**
       * @see Image#getSelf()
       */
      public T self(String self) {
         this.self = self;
         return self();
      }

      /**
       * @see Image#getFile()
       */
      public T file(String file) {
         this.file = file;
         return self();
      }

      /**
       * @see Image#getSchema()
       */
      public T schema(String schema) {
         this.schema = schema;
         return self();
      }

      /**
       * @see Image#getTags()
       */
      public T tags(List<String> tags) {
         this.tags = ImmutableList.copyOf(checkNotNull(tags, "tags"));
         return self();
      }

      /**
       * @see Image#deleted()
       */
      public T deleted(boolean deleted) {
         this.deleted = deleted;
         return self();
      }

      /**
       * @see Image#getContainerFormat()
       */
      public T containerFormat(ContainerFormat containerFormat) {
         this.containerFormat = containerFormat;
         return self();
      }

      /**
       * @see Image#getDiskFormat()
       */
      public T diskFormat(DiskFormat diskFormat) {
         this.diskFormat = diskFormat;
         return self();
      }

      /**
       * @see Image#getMinDisk()
       */
      public T minDisk(long minDisk) {
         this.minDisk = minDisk;
         return self();
      }

      /**
       * @see Image#getMinRam()
       */
      public T minRam(long minRam) {
         this.minRam = minRam;
         return self();
      }

      /**
       * @see Image#getOwner()
       */
      public T owner(String owner) {
         this.owner = owner;
         return self();
      }

      /**
       * @see Image#getDeletedAt()
       */
      public T deletedAt(Date deletedAt) {
         this.deletedAt = deletedAt;
         return self();
      }

      /**
       * @see Image#isPublic()
       */
      public T isPublic(boolean isPublic) {
         this.isPublic = isPublic;
         return self();
      }

      /**
       * @see Image#getProperties()
       */
      public T properties(Map<String, String> properties) {
         this.properties = ImmutableMap.copyOf(checkNotNull(properties, "properties"));
         return self();
      }

      public Image build() {
         return new Image(id, name, links, status, visibility, size, checksum, createdAt, updatedAt, self, file,
               schema, tags, deleted, containerFormat, diskFormat, minDisk, minRam, owner, deletedAt, isPublic,
               properties);
      }

      public T fromImage(Image in) {
         return super.fromResource(in).status(in.getStatus()).size(in.getSize()).checksum(in.getChecksum())
               .updatedAt(in.getUpdatedAt()).createdAt(in.getCreatedAt()).visibility(in.getVisibility())
               .self(in.getSelf()).file(in.getFile()).tags(in.getTags()).schema(in.getSchema()).deleted(in.isDeleted())
               .containerFormat(in.getContainerFormat()).diskFormat(in.getDiskFormat()).minDisk(in.getMinDisk())
               .minRam(in.getMinRam()).owner(in.getOwner()).deletedAt(in.getDeletedAt()).isPublic(in.isPublic())
               .properties(in.getProperties());
      }
   }

   private static class ConcreteBuilder extends Builder<ConcreteBuilder> {
      @Override
      protected ConcreteBuilder self() {
         return this;
      }
   }

   private Image.Status status;
   private Long size;
   private String checksum;
   @Named("updated_at")
   private Date updatedAt;
   @Named("created_at")
   private Date createdAt;
   private String visibility;
   private String file;
   private String self;
   private String schema;
   private ImmutableList<String> tags = ImmutableList.of();
   private boolean deleted;
   @Named("container_format")
   private ContainerFormat containerFormat;
   @Named("disk_format")
   private DiskFormat diskFormat;
   @Named("min_disk")
   private long minDisk;
   @Named("min_ram")
   private long minRam;
   private String owner;
   @Named("deleted_at")
   private Date deletedAt;
   @Named("is_public")
   private Boolean isPublic; // TODO: Is this here?
   private ImmutableMap<String, String> properties = ImmutableMap.of();

   @ConstructorProperties({ "id", "name", "links", "status", "visibility", "size", "checksum", "created_at",
         "updated_at", "self", "file", "schema", "tags", "deleted", "container_format", "disk_format", "min_disk",
         "min_ram", "owner", "deleted_at", "is_public", "properties" })
   protected Image(String id, @Nullable String name, Set<Link> links, Image.Status status, @Nullable String visibility,
         @Nullable Long size, @Nullable String checksum, Date createdAt, Date updatedAt, @Nullable String self,
         @Nullable String file, @Nullable String schema, @Nullable List<String> tags, @Nullable boolean deleted,
         @Nullable ContainerFormat containerFormat, @Nullable DiskFormat diskFormat, @Nullable long minDisk,
         @Nullable long minRam, @Nullable String owner, @Nullable Date deletedAt, @Nullable Boolean isPublic,
         @Nullable Map<String, String> properties) {
      super(id, name, links);
      this.status = checkNotNull(status, "status");
      this.size = size;
      this.checksum = checksum;
      this.updatedAt = checkNotNull(updatedAt, "updated_at");
      this.createdAt = checkNotNull(createdAt, "created_at");
      this.visibility = visibility;
      this.self = self;
      this.file = file;
      this.schema = schema;
      if (tags != null)
         this.tags = ImmutableList.copyOf(tags);
      this.deleted = deleted;
      this.containerFormat = containerFormat;
      this.diskFormat = diskFormat;
      this.minDisk = minDisk;
      this.minRam = minRam;
      this.owner = owner;
      this.deletedAt = deletedAt;
      this.isPublic = (isPublic == null) ? false : isPublic;
      if (properties != null)
         this.properties = ImmutableMap.copyOf(properties);
   }

   public Image.Status getStatus() {
      return this.status;
   }

   public Long getSize() {
      return this.size;
   }

   public String getChecksum() {
      return this.checksum;
   }

   public Date getUpdatedAt() {
      return this.updatedAt;
   }

   public Date getCreatedAt() {
      return this.createdAt;
   }

   public String getVisibility() {
      return this.visibility;
   }

   public String getSelf() {
      return this.self;
   }

   public String getFile() {
      return this.file;
   }

   public String getSchema() {
      return this.schema;
   }

   public List<String> getTags() {
      return this.tags;
   }

   /**
    * This is deleted flag
    */
   public boolean isDeleted() {
      return this.deleted;
   }

   public ContainerFormat getContainerFormat() {
      return this.containerFormat;
   }

   public DiskFormat getDiskFormat() {
      return this.diskFormat;
   }

   /**
    * Note this could be zero if unset
    */
   public long getMinDisk() {
      return this.minDisk;
   }

   /**
    * Note this could be zero if unset
    */
   public long getMinRam() {
      return this.minRam;
   }

   public String getOwner() {
      return this.owner;
   }

   public Date getDeletedAt() {
      return this.deletedAt;
   }

   public boolean isPublic() {
      return this.isPublic;
   }

   public Map<String, String> getProperties() {
      return this.properties;
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(super.hashCode(), status, size, checksum, updatedAt, createdAt, visibility, self, file,
            schema, tags, deleted, minDisk, minRam, owner, deletedAt, isPublic, properties);
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null || getClass() != obj.getClass())
         return false;
      Image that = Image.class.cast(obj);
      return super.equals(that) && Objects.equal(this.status, that.status) && Objects.equal(this.size, that.size)
            && Objects.equal(this.checksum, that.checksum) && Objects.equal(this.updatedAt, that.updatedAt)
            && Objects.equal(this.createdAt, that.createdAt) && Objects.equal(this.visibility, that.visibility)
            && Objects.equal(this.self, that.self) && Objects.equal(this.file, that.file)
            && Objects.equal(this.schema, that.schema) && Objects.equal(this.tags, that.tags)
            && Objects.equal(this.minDisk, that.minDisk) && Objects.equal(this.deleted, that.deleted)
            && Objects.equal(this.minRam, that.minRam) && Objects.equal(this.owner, that.owner)
            && Objects.equal(this.deletedAt, that.deletedAt) && Objects.equal(this.isPublic, that.isPublic)
            && Objects.equal(this.properties, that.properties);
   }

   protected ToStringHelper string() {
      return super.string().add("status", status).add("size", size).add("checksum", checksum)
            .add("updatedAt", updatedAt).add("createdAt", createdAt).add("visibility", visibility).add("self", self)
            .add("file", file).add("schema", schema).add("tags", tags).add("deleted", deleted).add("minDisk", minDisk)
            .add("minRam", minRam).add("owner", owner).add("deletedAt", deletedAt).add("isPublic", isPublic)
            .add("properties", properties);
   }

}
