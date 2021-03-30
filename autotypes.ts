interface AutoJSON {
    steps: []
}

type Double = number;

type Step = {
    type: "setHeading",
    heading: Double
} | {
    type: "path",
    points: { x: Double, y: Double }[]
}