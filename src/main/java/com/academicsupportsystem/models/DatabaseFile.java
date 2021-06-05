package com.academicsupportsystem.models;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "files")
public class DatabaseFile {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @OnDelete(action = OnDeleteAction.CASCADE)
    private String fileName;

    @OnDelete(action = OnDeleteAction.CASCADE)
    private String fileType;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="generated_by_user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User generatedBy;
    
    @Lob
    private byte[] data;

    public DatabaseFile() {

    }

    public DatabaseFile(String fileName, String fileType, byte[] data, User generatedBy) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
        this.generatedBy = generatedBy;
    }
    

    public User getGeneratedBy() {
		return generatedBy;
	}

	public void setGeneratedBy(User generatedBy) {
		this.generatedBy = generatedBy;
	}

	public String getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public byte[] getData() {
        return data;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}