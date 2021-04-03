interface AutoJSON {
    steps: Step[]
}

type Double = number;

type Step = {
//     type: "setHeading",
//     heading: Double
// } | {
    type: "path",
    points: { x: Double, y: Double, θ: Double}[]
}

const test: AutoJSON = {
    steps: [
        {
            type: "path",
            points: {
                x: 10,
                y:10.0, 
                theta: 10.0
            }
        },
    ]
}