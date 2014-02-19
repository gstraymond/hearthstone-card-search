package fr.gstraymond.search.model.response;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Card implements Parcelable {
	private String title;
	private String frenchTitle;
	private String type;
	private String castingCost;
	private String power;
	private String toughness;
	private String description;
	private List<Publication> publications;
	private List<String> formats;

	public static final Parcelable.Creator<Card> CREATOR = new Parcelable.Creator<Card>() {
		@Override
		public Card createFromParcel(Parcel source) {
			return new Card(source);
		}

		@Override
		public Card[] newArray(int size) {
			return new Card[size];
		}
	};

	public Card() {
	}

	public Card(Parcel source) {
		title = source.readString();
		frenchTitle = source.readString();
		type = source.readString();
		castingCost = source.readString();
		power = source.readString();
		toughness = source.readString();
		description = source.readString();
		publications = new ArrayList<Publication>();
		source.readList(publications, Publication.class.getClassLoader());
		formats = new ArrayList<String>();
		source.readList(formats, String.class.getClassLoader());
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(title);
		dest.writeString(frenchTitle);
		dest.writeString(type);
		dest.writeString(castingCost);
		dest.writeString(power);
		dest.writeString(toughness);
		dest.writeString(description);
		dest.writeList(publications);
		dest.writeList(formats);
	}

	@Override
	public String toString() {
		return title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCastingCost() {
		return castingCost;
	}

	public void setCastingCost(String castingCost) {
		this.castingCost = castingCost;
	}
	

	public String getFrenchTitle() {
		return frenchTitle;
	}

	public void setFrenchTitle(String frenchTitle) {
		this.frenchTitle = frenchTitle;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getToughness() {
		return toughness;
	}

	public void setToughness(String toughness) {
		this.toughness = toughness;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Publication> getPublications() {
		return publications;
	}

	public void setPublications(List<Publication> publications) {
		this.publications = publications;
	}

	public List<String> getFormats() {
		return formats;
	}

	public void setFormats(List<String> formats) {
		this.formats = formats;
	}
}