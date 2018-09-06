package org.molgenis.semanticsearch.explain.service;

import java.util.Map;
import java.util.Set;
import org.apache.lucene.search.Explanation;
import org.molgenis.data.Entity;
import org.molgenis.data.Query;
import org.molgenis.data.meta.model.EntityType;
import org.molgenis.semanticsearch.explain.bean.ExplainedQueryString;

public interface ElasticSearchExplainService {
  /** Get explanation for a specific document in elasticSearch */
  Explanation explain(Query<Entity> q, EntityType entityType, Object entityId);

  /** Deduce all the matches that are generated by ElasticSearch */
  Set<ExplainedQueryString> findQueriesFromExplanation(
      Map<String, String> collectExpandedQueryMap, Explanation explanation);
}
