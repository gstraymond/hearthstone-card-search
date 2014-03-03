package fr.gstraymond.search.model.response;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Card implements Parcelable {
	private String attack;
	private List<String> capabilities;
	private String castingCost;
	private String clazz;
	private String classCode;
	private String description;
	private String elite;
	private String health;
	private String image;
	private String minionType;
	private String rarity;
	private String rarityCode;
	private String set;
	private String title;
	private String type;

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
		capabilities = new ArrayList<String>();
		
		attack = source.readString();
		source.readList(capabilities, String.class.getClassLoader());
		castingCost = source.readString();
		clazz = source.readString();
		classCode = source.readString();
		description = source.readString();
		elite = source.readString();
		health = source.readString();
		image = source.readString();
		minionType = source.readString();
		rarity = source.readString();
		rarityCode = source.readString();
		title = source.readString();
		type = source.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(attack);
		dest.writeList(capabilities);
		dest.writeString(castingCost);
		dest.writeString(clazz);
		dest.writeString(classCode);
		dest.writeString(description);
		dest.writeString(elite);
		dest.writeString(health);
		dest.writeString(image);
		dest.writeString(minionType);
		dest.writeString(rarity);
		dest.writeString(rarityCode);
		dest.writeString(title);
		dest.writeString(type);
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAttack() {
		return attack;
	}

	public void setAttack(String attack) {
		this.attack = attack;
	}

	public String getRarity() {
		return rarity;
	}

	public void setRarity(String rarity) {
		this.rarity = rarity;
	}

	public String getRarityCode() {
		return rarityCode;
	}

	public void setRarityCode(String rarityCode) {
		this.rarityCode = rarityCode;
	}

	public String getSet() {
		return set;
	}

	public void setSet(String set) {
		this.set = set;
	}

	public String getHealth() {
		return health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getElite() {
		return elite;
	}

	public void setElite(String elite) {
		this.elite = elite;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getMinionType() {
		return minionType;
	}

	public void setMinionType(String minionType) {
		this.minionType = minionType;
	}

	public List<String> getCapabilities() {
		return capabilities;
	}

	public void setCapabilities(List<String> capabilities) {
		this.capabilities = capabilities;
	}
}