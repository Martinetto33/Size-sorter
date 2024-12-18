package main.model

import main.data.*

data class ArticleDimension(
    val region: Regions,
    val sizeName: SizeNames?,
    val size: Int,
    val areAllPropertiesInitialised: Boolean = false
) {
    /**
     * These state variables are used to determine what to print when
     * the [toString] method is called.
     * */
    private var wasRegionGiven: Boolean = false
    private var wasSizeNameGiven: Boolean = false
    private var wasSizeGiven: Boolean = false

    init {
        if (areAllPropertiesInitialised) {
            wasRegionGiven = true
            wasSizeNameGiven = true
            wasSizeGiven = true
        }
    }

    // Public constructors
    constructor(region: Regions, sizeName: SizeNames) : this(region, sizeName, determineSizeFromName(sizeName, region)) {
        this.wasRegionGiven = true
        this.wasSizeNameGiven = true
    }

    constructor(sizeName: SizeNames) : this(defaultRegion, sizeName, determineSizeFromName(sizeName, defaultRegion)) {
        this.wasSizeNameGiven = true
    }

    constructor(size: Int) : this(defaultRegion, size, wasRegGiven = false)

    constructor(region: Regions, size: Int) : this(region, size, wasRegGiven = true)

    // Private constructors
    private constructor(region: Regions, parameterSize: Int, wasRegGiven: Boolean) : this(
        region,
        determineNameFromSize(parameterSize, region),
        parameterSize
    ) {
        this.wasRegionGiven = wasRegGiven
        this.wasSizeGiven = true
    }

    /**
     * This object will be printed according to the information passed
     * at its creation. Even though the object contains all the relevant
     * information for sorting purposes, the state keeping record of the
     * parameters passed upon creation allows to print exactly what the
     * user input.
     * */
    override fun toString(): String {
        if (wasRegionGiven && wasSizeGiven) {
            return "${this.region} ${this.size}"
        }
        if (wasSizeGiven) {
            return this.size.toString()
        }
        if (wasSizeNameGiven) {
            return this.sizeName.toString()
        }
        throw IllegalStateException("Cannot convert $this to String")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ArticleDimension
        if (region != other.region) return false
        if (size != other.size) return false
        if (sizeName != other.sizeName) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}
