package fr.gstraymond.search.model.response;

import android.os.Parcel;
import android.os.Parcelable;

public class Publication implements Parcelable {

	private String edition;
	private String editionCode;
	private String stdEditionCode;
	private String rarity;
	private String rarityCode;
	private String image;
	private String editionImage;

	public static final Parcelable.Creator<Publication> CREATOR = new Parcelable.Creator<Publication>() {
		@Override
		public Publication createFromParcel(Parcel source) {
			return new Publication(source);
		}

		@Override
		public Publication[] newArray(int size) {
			return new Publication[size];
		}
	};

	public Publication() {
	}

	public Publication(Parcel source) {
		edition = source.readString();
		editionCode = source.readString();
		stdEditionCode = source.readString();
		rarity = source.readString();
		rarityCode = source.readString();
		image = source.readString();
		editionImage = source.readString();
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(edition);
		dest.writeString(editionCode);
		dest.writeString(stdEditionCode);
		dest.writeString(rarity);
		dest.writeString(rarityCode);
		dest.writeString(image);
		dest.writeString(editionImage);
	}
	
	public String getRarity() {
		return rarity;
	}
	public void setRarity(String rarity) {
		this.rarity = rarity;
	}
	public String getEdition() {
		return edition;
	}
	public void setEdition(String edition) {
		this.edition = edition;
	}
	public String getEditionCode() {
		return editionCode;
	}
	public void setEditionCode(String editionCode) {
		this.editionCode = editionCode;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	public String getStdEditionCode() {
		return stdEditionCode;
	}

	public void setStdEditionCode(String stdEditionCode) {
		this.stdEditionCode = stdEditionCode;
	}

	public String getRarityCode() {
		return rarityCode;
	}

	public void setRarityCode(String rarityCode) {
		this.rarityCode = rarityCode;
	}

	public String getEditionImage() {
		return editionImage;
	}

	public void setEditionImage(String editionImage) {
		this.editionImage = editionImage;
	}
}
