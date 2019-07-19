package com.aicheck.face.modules.advertisingImages.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "image_strategy")
public class ImageStrategy {
	 	@Id
	    @GeneratedValue(strategy=GenerationType.IDENTITY)
	    private Integer id;
	 
	 	private int group_id;
	 	private String name;
	 	private String strategy;
	 	
	 	@CreationTimestamp
	    private Date createTime;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public int getGroup_id() {
			return group_id;
		}

		public void setGroup_id(int group_id) {
			this.group_id = group_id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getStrategy() {
			return strategy;
		}

		public void setStrategy(String strategy) {
			this.strategy = strategy;
		}

		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

		@Override
		public String toString() {
			return "ImageStrategy [id=" + id + ", group_id=" + group_id + ", name=" + name + ", strategy=" + strategy
					+ ", createTime=" + createTime + "]";
		}
		
}
