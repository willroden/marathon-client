package mesosphere.marathon.client.model.v2;

public enum ConstraintOperator {
	// Per the Constraint.Operator enum defined in file
	//   marathon/src/main/proto/marathon.proto
	// and the JSON schema defined in file
	//   marathon/docs/docs/rest-api/public/api/v2/schema/AppDefinition.json:
	UNIQUE,
	CLUSTER,
	GROUP_BY,
	LIKE,
	UNLIKE;
}
