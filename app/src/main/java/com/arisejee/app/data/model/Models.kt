package com.arisejee.app.data.model

data class Subject(val name: String, val chapters: List<Chapter>)
data class Chapter(val name: String, val subject: String)

enum class Difficulty(val label: String, val baseXp: Int) {
    EASY("Easy", 10), MEDIUM("Medium", 25), HARD("Hard", 50)
}

enum class ExamType(val label: String) {
    JEE_MAINS("JEE Mains"), JEE_ADVANCED("JEE Advanced")
}

enum class Rank(val title: String, val xpRequired: Int, val xpMultiplier: Float) {
    E("E-Rank", 0, 1.0f),
    D("D-Rank", 500, 0.95f),
    C("C-Rank", 1500, 0.85f),
    B("B-Rank", 3500, 0.75f),
    A("A-Rank", 7000, 0.6f),
    S("S-Rank", 15000, 0.45f),
    NATIONAL("National Level", 30000, 0.3f);

    companion object {
        fun fromXp(xp: Int): Rank = entries.lastOrNull { xp >= it.xpRequired } ?: E
        fun fromString(s: String): Rank = entries.firstOrNull { it.name == s } ?: E
    }
}

data class WeakTopic(
    val subject: String,
    val chapter: String,
    val wrongCount: Int,
    val avgTimeSeconds: Float,
    val totalAttempts: Int,
    val severity: String
)

object SubjectData {
    val physics = Subject("Physics", listOf(
        "Kinematics","Laws of Motion","Work Energy Power","Rotational Mechanics",
        "Gravitation","Properties of Matter","SHM and Waves","Thermodynamics",
        "Heat Transfer","Optics","Electrostatics","Current Electricity",
        "Magnetism","EMI and AC","Modern Physics"
    ).map { Chapter(it, "Physics") })

    val chemistry = Subject("Chemistry", listOf(
        "Atomic Structure","Chemical Bonding","States of Matter","Thermochemistry",
        "Chemical Equilibrium","Ionic Equilibrium","Solid State","Solutions",
        "Electrochemistry","Chemical Kinetics","Surface Chemistry",
        "General Organic Chemistry","Hydrocarbons","Functional Groups","Coordination Compounds"
    ).map { Chapter(it, "Chemistry") })

    val maths = Subject("Maths", listOf(
        "Sets and Relations","Complex Numbers","Quadratic Equations","Sequences and Series",
        "Permutations and Combinations","Binomial Theorem","Matrices and Determinants",
        "Limits and Continuity","Differentiation","Integration","Differential Equations",
        "Straight Lines","Circles and Conics","3D Geometry and Vectors","Probability"
    ).map { Chapter(it, "Maths") })

    val all = listOf(physics, chemistry, maths)
}
