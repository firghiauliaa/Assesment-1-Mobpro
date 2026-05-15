import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "fuel_history")
data class FuelEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val jarak: Float,
    val konsumsi: Float,
    val harga: Float,
    val totalBiaya: Double,
    val tanggal: Long = System.currentTimeMillis()
)